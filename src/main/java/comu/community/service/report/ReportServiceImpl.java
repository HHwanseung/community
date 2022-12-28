package comu.community.service.report;

import comu.community.dto.report.BoardReportRequest;
import comu.community.dto.report.BoardReportResponse;
import comu.community.dto.report.UserReportRequest;
import comu.community.dto.report.UserReportResponse;
import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.board.Board;
import comu.community.entity.report.BoardReportHistory;
import comu.community.entity.report.UserReportHistory;
import comu.community.entity.user.User;
import comu.community.exception.AlreadyReportException;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.MemberNotFoundException;
import comu.community.exception.NotSelfReportException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.report.BoardReportRepository;
import comu.community.repository.report.UserReportRepository;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{
    private final static long NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED = 1;
    private final static long NORMAL_BOARD_REPORT_LIMIT_FOR_BEING_REPORTED = 10L;
    private final UserReportRepository userReportHistoryRepository;
    private final BoardReportRepository boardReportHistoryRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    @Override
    public UserReportResponse reportUser(User reporter, UserReportRequest req) {
        validateUserReportRequest(reporter, req);
        User reportedUser = userRepository.findById(req.getReportedUserId()).orElseThrow(MemberNotFoundException::new);
        UserReportHistory userReportHistory = createUserReportHistory(reporter, reportedUser, req);
        checkUserStatusIsBeingReported(reportedUser, req);
        return new UserReportResponse(userReportHistory.getId(), UserEditRequestDto.toDto(reportedUser),
                req.getContent());
    }

    private void checkUserStatusIsBeingReported(User reportedUser, UserReportRequest req) {
        if (userReportHistoryRepository.findByReportedUserId(req.getReportedUserId()).size() >= NORMAL_USER_REPORT_LIMIT_FOR_BEING_REPORTED) {
            reportedUser.setStatusIsBeingReported();
        }
    }

    private UserReportHistory createUserReportHistory(User reporter, User reportedUser, UserReportRequest req) {
        UserReportHistory userReportHistory = new UserReportHistory(reporter.getId(), reportedUser.getId(), req.getContent());
        userReportHistoryRepository.save(userReportHistory);
        return userReportHistory;
    }

    private void validateUserReportRequest(User reporter, UserReportRequest req) {
        if (reporter.isReportMySelf(req.getReportedUserId())) {
            throw new NotSelfReportException();
        }

        if (userReportHistoryRepository.existsByReporterIdAndReportedUserId(reporter.getId(),
                req.getReportedUserId())) {
            throw new AlreadyReportException();
        }
    }

    @Override
    public BoardReportResponse reportBoard(User reporter, BoardReportRequest req) {
        Board reportedBoard = boardRepository.findById(req.getReportedBoardId()).orElseThrow(BoardNotFoundException::new);

        if (reporter.getId() == reportedBoard.getUser().getId()){
            throw new NotSelfReportException();
        }

        if (boardReportHistoryRepository.findByReporterIdAndReportedBoardId(reporter.getId(), req.getReportedBoardId()) == null) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리
            BoardReportHistory boardReport = new BoardReportHistory(reporter.getId(), reportedBoard.getId(), req.getContent());
            boardReportHistoryRepository.save(boardReport);

            if (boardReportHistoryRepository.findByReportedBoardId(req.getReportedBoardId()).size() >= 10) {
                reportedBoard.setReported(true);
            }

            BoardReportResponse res = new BoardReportResponse(boardReport.getId(), req.getReportedBoardId(), req.getContent());
            return res;
        } else {
            throw new AlreadyReportException();
        }

    }
}
