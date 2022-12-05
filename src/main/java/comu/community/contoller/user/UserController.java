package comu.community.contoller.user;

import comu.community.dto.user.UserDto;
import comu.community.entity.user.User;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.user.UserRepository;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import comu.community.response.Response;
import comu.community.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(value = "User Controller", tags = "User")
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @ApiOperation(value = "회원 전체 조회", notes = "회원 전체를 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public Response findAllUser() {
        return Response.success(userService.findAllUsers());

    }

    @ApiOperation(value = "회원 개별 조회", notes = "개뵬 회원을 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public Response findUser(@ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        return Response.success(userService.findUser(id));

    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/users/{id}")
    public Response updateUserInfo(@ApiParam(value = "User ID", required = true) @PathVariable Long id, @RequestBody UserDto userDto) {
        return Response.success(userService.updateUserInfo(id,userDto));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원을 탈퇴 시킴")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users/{id}")
    public Response deleteUserInfo(@ApiParam(value = "User ID", required = true) @PathVariable Long id) {
        userService.deleteUserInfo(id);
        return Response.success();
    }

}
