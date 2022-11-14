package comu.community.service.auth;

import comu.community.config.jwt.TokenProvider;
import comu.community.dto.sign.RegisterDto;
import comu.community.dto.sign.SignUpRequestDto;
import comu.community.entity.user.Role;
import comu.community.entity.user.User;
import comu.community.repository.refreshToken.RefreshTokenRepository;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public void signup(SignUpRequestDto req) {
        validateSignUpInfo(req);

        // Builder로 리팩토링
//        User user = new User();
//        user.setUsername(req.getUsername());
//        user.setPassword(req.getPassword());
//        user.setNickname(req.getNickname());
//        user.setName(req.getName());
//        user.setRole(Role.ROLE_USER);

        User user = User.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);

    }

    private void validateSignUpInfo(SignUpRequestDto signUpRequestDto) {
    }
}
