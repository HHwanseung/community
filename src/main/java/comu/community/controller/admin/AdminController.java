package comu.community.controller.admin;

import comu.community.response.Response;
import comu.community.service.admin.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Admin Controller", tags = "Admin")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;

    @ApiOperation(value = "정지 유저 관리")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/manages/users")
    public Response ManageReportedUser() {
        return Response.success(adminService.manageReportedUser());
    }



}
