package comu.community.controller.report;

import comu.community.dto.report.BoardReportRequest;
import comu.community.dto.report.UserReportRequest;
import comu.community.entity.user.User;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.user.UserRepository;
import comu.community.response.Response;
import comu.community.service.report.ReportService;
import comu.community.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Report Controller", tags = "Report")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReportController {

    private final ReportService reportService;
    private final UserRepository userRepository;

    @ApiOperation(value = "유저 신고", notes = "유저를 신고합니다")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reports/users")
    public Response reportUSer(@Valid @RequestBody UserReportRequest userReportRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        return Response.success(reportService.reportUser(user, userReportRequest));
    }

    @ApiOperation(value = "게시글 신고", notes = "게시글을 신고합니다")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reports/boards")
    public Response reportBoard(@Valid @RequestBody BoardReportRequest boardReportRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        return Response.success(reportService.reportBoard(user, boardReportRequest));
    }
}
