package comu.community.controller.auth;

import comu.community.dto.sign.LoginRequestDto;
import comu.community.dto.sign.SignUpRequestDto;
import comu.community.dto.sign.TokenRequestDto;
import comu.community.entity.member.Member;
import comu.community.response.Response;
import comu.community.service.auth.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static comu.community.response.Response.success;

@Api(value = "Sing Controller", tags = "Sign")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원가입", notes = "회원가입 진행")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public Response register(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        Member member = authService.signup(signUpRequestDto);
        authService.savePointEntity(member);
        return success();
    }

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/sign-in")
    public Response signIn(@Valid @RequestBody LoginRequestDto req) {
        return success(authService.signIn(req));

    }
    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급 요청")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reissue")
    public Response reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return success(authService.reissue(tokenRequestDto));
    }

}
