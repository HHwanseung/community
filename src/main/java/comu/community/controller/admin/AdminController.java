package comu.community.controller.admin;

import comu.community.response.Response;
import comu.community.service.admin.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "Admin Controller", tags = "Admin")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    @ApiOperation(value = "정지 유저 관리")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/manages/users")
    public Response manageReportedUser() {
        return Response.success(adminService.findReportedUsers());
    }

    @ApiOperation(value = "신고된 유저 정지 해제")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/admin/manages/users/{id}")
    public Response unlockUser(@PathVariable Long id) {
        return Response.success(adminService.processUnlockUser(id));
    }

    @ApiOperation(value = "게시물 관리")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/manages/boards")
    public Response manageReportBoards() {
        return Response.success(adminService.findReportedBoards());
    }

    @ApiOperation(value = "신고된 게시물 관리")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping ("/admin/manages/boards/{id}")
    public Response unlockBoard(@PathVariable Long id) {
        return Response.success(adminService.processUnlockBoard(id));
    }

}
