package comu.community.service.auth;

import comu.community.dto.sign.LoginRequestDto;
import comu.community.dto.sign.SignUpRequestDto;
import comu.community.dto.sign.TokenRequestDto;
import comu.community.dto.sign.TokenResponseDto;
import comu.community.entity.member.Member;

public interface AuthService {

    void signup(SignUpRequestDto req);
    void savePointEntity(Member member);
    TokenResponseDto signIn(LoginRequestDto loginRequestDto);
    TokenResponseDto reissue(TokenRequestDto tokenRequestDto);



}
