package comu.community.service.auth;

import comu.community.config.jwt.TokenProvider;
import comu.community.dto.sign.*;
import comu.community.entity.member.RefreshToken;
import comu.community.entity.member.Role;
import comu.community.entity.member.Member;
import comu.community.exception.LoginFailureException;
import comu.community.exception.MemberNicknameAlreadyExistsException;
import comu.community.exception.UsernameAlreadyExistsException;
import comu.community.repository.refreshToken.RefreshTokenRepository;
import comu.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void signup(SignUpRequestDto req) {
        validateSignUpInfo(req);
        Member member = createSignupFormOfUser(req);
        memberRepository.save(member);

    }

    @Override
    public TokenResponseDto signIn(LoginRequestDto req) {
        Member member = memberRepository.findByUsername(req.getUsername()).orElseThrow(() -> {
            throw new LoginFailureException();
        });

        validatePassword(req, member);
        Authentication authentication = getUserAuthentication(req);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken refreshToken = buildRefreshToken(authentication, tokenDto);
        refreshTokenRepository.save(refreshToken);
        return new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    private RefreshToken buildRefreshToken(Authentication authentication, TokenDto tokenDto) {
        return RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();
    }

    private Authentication getUserAuthentication(LoginRequestDto req) {
        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return authentication;
    }

    @Override
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        validateRefreshToken(tokenRequestDto);

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));
        validateRefreshTokenOwner(refreshToken, tokenRequestDto);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        TokenResponseDto tokenResponseDto = new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return tokenResponseDto;
    }

    private Member createSignupFormOfUser(SignUpRequestDto req) {
        Member member = Member.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .nickname(req.getNickname())
                .name(req.getName())
                .role(Role.ROLE_USER)
                .build();
        return member;
    }

    private void validateSignUpInfo(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByUsername(signUpRequestDto.getUsername())) {
            throw new UsernameAlreadyExistsException(signUpRequestDto.getUsername());
        }
        if (memberRepository.existsByNickname(signUpRequestDto.getNickname())) {
            throw new MemberNicknameAlreadyExistsException(signUpRequestDto.getNickname());
        }
    }

    private void validatePassword(LoginRequestDto loginRequestDto, Member member) {
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private void validateRefreshToken(TokenRequestDto tokenRequestDto) {
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }
    }

    private void validateRefreshTokenOwner(RefreshToken refreshToken, TokenRequestDto tokenRequestDto) {
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }
    }

//    @Override
//    public TokenResponseDto signIn(LoginRequestDto req) {
//        User user = userRepository.findByUsername(req.getUsername()).orElseThrow(() -> {
//            return new LoginFailureException();
//        });
//
//        validatePassword(req, user);
//        UsernamePasswordAuthenticationToken authenticationToken = req.toAuthentication();
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//
//        redisService.setValues(authentication.getName(), tokenDto.getRefreshToken());
//        TokenResponseDto tokenResponseDto = new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
//        return tokenResponseDto;
//    }
//
//
//    @Override
//    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
//        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
//        redisService.checkRefreshToken(authentication.getName(), tokenRequestDto.getRefreshToken());
//
//        // 예외 처리 통과후 토큰 재생성
//        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
//        TokenResponseDto tokenResponseDto = new TokenResponseDto(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
//        return tokenResponseDto;
//    }
//
//
//    private void validateSignUpInfo(SignUpRequestDto signUpRequestDto) {
//        if (userRepository.existsByUsername(signUpRequestDto.getUsername()))
//            throw new MemberUsernameAlreadyExistsException(signUpRequestDto.getUsername());
//        if (userRepository.existsByNickname(signUpRequestDto.getNickname()))
//            throw new MemberNicknameAlreadyExistsException(signUpRequestDto.getNickname());
//    }
//
//    private void validatePassword(LoginRequestDto loginRequestDto, User user) {
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//            throw new LoginFailureException();
//        }
//    }
}
