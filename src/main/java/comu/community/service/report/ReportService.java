package comu.community.service.report;

import comu.community.dto.report.BoardReportRequest;
import comu.community.dto.report.BoardReportResponse;
import comu.community.dto.report.MemberReportRequestDto;
import comu.community.dto.report.MemberReportResponseDto;
import comu.community.entity.member.Member;

public interface ReportService {

    MemberReportResponseDto reportUser(Member reporter, MemberReportRequestDto req);
    BoardReportResponse reportBoard(Member reporter, BoardReportRequest req);


}
