package comu.community.service.auth;

import comu.community.config.jwt.TokenProvider;
import comu.community.dto.sign.LoginRequestDto;
import comu.community.dto.sign.SignUpRequestDto;
import comu.community.entity.member.RefreshToken;
import comu.community.exception.LoginFailureException;
import comu.community.repository.member.MemberRepository;
import comu.community.repository.refreshToken.RefreshTokenRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static comu.community.factory.MemberFactory.createMember;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock
    AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    TokenProvider tokenProvider;
    @Mock
    RefreshToken refreshToken;
    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @Test
    void sign_up() {
        //given
        SignUpRequestDto req = new SignUpRequestDto("username", "1234", "name", "nickname");
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(req.getPassword())).thenReturn(encodedPassword);

        //when
        authService.signup(req);

        //then
        verify(passwordEncoder).encode(req.getPassword());
        verify(memberRepository).save(any());

    }

    @Test
    void login_failure() {
        //given
        given(memberRepository.findByUsername(any())).willReturn(Optional.empty());

//        assertThatThrownBy(() -> authService.signIn(new LoginRequestDto("username","password")))
//                .isInstanceOf(LoginFailureException.class);
        
        assertThrows(LoginFailureException.class,
                () -> authService.signIn(new LoginRequestDto("username", "password")));
    }

    @Test
    void password_invalid(){
        //given
        given(memberRepository.findByUsername(any())).willReturn(Optional.of(createMember()));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        //when, then
        assertThrows(LoginFailureException.class,
                () -> authService.signIn(new LoginRequestDto("username", "password")));
    }

}