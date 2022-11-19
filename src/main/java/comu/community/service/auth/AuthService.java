package comu.community.service.auth;

import comu.community.dto.sign.LoginRequestDto;
import comu.community.dto.sign.SignUpRequestDto;
import comu.community.dto.sign.TokenRequestDto;
import comu.community.dto.sign.TokenResponseDto;

public interface AuthService {

    void signup(SignUpRequestDto req);
    TokenResponseDto signIn(LoginRequestDto loginRequestDto);
    TokenResponseDto reissue(TokenRequestDto tokenRequestDto);



}
