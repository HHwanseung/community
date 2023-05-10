package comu.community.service.report;

import comu.community.dto.report.BoardReportRequest;
import comu.community.dto.report.BoardReportResponse;
import comu.community.dto.report.MemberReportRequestDto;
import comu.community.dto.report.MemberReportResponseDto;
import comu.community.dto.member.MemberEditRequestDto;
import comu.community.entity.board.Board;
import comu.community.entity.report.BoardReport;
import comu.community.entity.report.MemberReport;
import comu.community.entity.member.Member;
import comu.community.exception.AlreadyReportException;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.MemberNotFoundException;
import comu.community.exception.NotSelfReportException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.report.BoardReportRepository;
import comu.community.repository.report.MemberReportRepository;
import comu.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService{
    private final static int NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED = 1;
    private final static int NORMAL_BOARD_REPORT_LIMIT_FOR_BEING_REPORTED = 10;
    private final MemberReportRepository userReportHistoryRepository;
    private final BoardReportRepository boardReportHistoryRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @Override
    public MemberReportResponseDto reportUser(Member reporter, MemberReportRequestDto req) {
        validateUserReportRequest(reporter, req);
        Member reportedMember = memberRepository.findById(req.getReportedUserId()).orElseThrow(MemberNotFoundException::new);
        MemberReport memberReport = createUserReportHistory(reporter, reportedMember, req);
        checkUserStatusIsBeingReported(reportedMember, req);
        return new MemberReportResponseDto(memberReport.getId(), MemberEditRequestDto.toDto(reportedMember),
                req.getContent());
    }

    private void checkUserStatusIsBeingReported(Member reportedMember, MemberReportRequestDto req) {
        if (userReportHistoryRepository.findByReportedUserId(req.getReportedUserId()).size() >= NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED) {
            reportedMember.setStatusIsBeingReported();
        }
    }

    private MemberReport createUserReportHistory(Member reporter, Member reportedMember, MemberReportRequestDto req) {
        MemberReport memberReport = new MemberReport(reporter.getId(), reportedMember.getId(), req.getContent());
        userReportHistoryRepository.save(memberReport);
        return memberReport;
    }

    private void validateUserReportRequest(Member reporter, MemberReportRequestDto req) {
        if (reporter.isReportMySelf(req.getReportedUserId())) {
            throw new NotSelfReportException();
        }

        if (userReportHistoryRepository.existsByReporterIdAndReportedUserId(reporter.getId(),
                req.getReportedUserId())) {
            throw new AlreadyReportException();
        }
    }

    @Override
    public BoardReportResponse reportBoard(Member reporter, BoardReportRequest req) {
        Board reportedBoard = boardRepository.findById(req.getReportedBoardId()).orElseThrow(BoardNotFoundException::new);
        validateBoard(reporter, reportedBoard, req);
        BoardReport boardReport = createBoardReportHistory(reporter, reportedBoard, req);
        checkBoardStatusIsBeingReported(reportedBoard, req);
        return new BoardReportResponse(boardReport.getId(), req.getReportedBoardId(),
                req.getContent());
    }

    private void checkBoardStatusIsBeingReported(Board reportedBoard, BoardReportRequest req) {
        if (boardReportHistoryRepository.findByReportedBoardId(req.getReportedBoardId()).size()
                >= NORMAL_BOARD_REPORT_LIMIT_FOR_BEING_REPORTED) {
            reportedBoard.setStatusIsBeingReported();
        }
    }

    private BoardReport createBoardReportHistory(Member reporter, Board reportedBoard, BoardReportRequest req) {
        BoardReport boardReport = new BoardReport(reporter.getId(), reportedBoard.getId(), req.getContent());
        boardReportHistoryRepository.save(boardReport);
        return boardReport;
    }

    private void validateBoard(Member reporter, Board reportedBoard, BoardReportRequest req) {
        if (reporter.isReportMySelf(reportedBoard.getMember().getId())) {
            throw new NotSelfReportException();
        }

        if (boardReportHistoryRepository.existsByReporterIdAndReportedBoardId(reporter.getId(), req.getReportedBoardId())) {
            throw new AlreadyReportException();
        }
    }
}
