package comu.community.service.report;

import comu.community.dto.report.BoardReportRequest;
import comu.community.dto.report.BoardReportResponse;
import comu.community.dto.report.UserReportRequest;
import comu.community.dto.report.UserReportResponse;
import comu.community.entity.user.User;

public interface ReportService {

    UserReportResponse reportUser(User reporter, UserReportRequest req);
    BoardReportResponse reportBoard(User reporter, BoardReportRequest req);


}
