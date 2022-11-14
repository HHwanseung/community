package comu.community.contoller.auth;

import comu.community.dto.sign.SignUpRequestDto;
import comu.community.response.Response;
import comu.community.service.auth.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static comu.community.response.Response.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "회원가입", notes = "회원가입 진행")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Response register(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        authService.signup(signUpRequestDto);
        return success();
    }

}
