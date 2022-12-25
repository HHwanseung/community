package comu.community.service.report;

import comu.community.dto.report.BoardReportRequest;
import comu.community.dto.report.BoardReportResponse;
import comu.community.dto.report.UserReportRequest;
import comu.community.dto.report.UserReportResponse;
import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.board.Board;
import comu.community.entity.report.BoardReport;
import comu.community.entity.report.UserReport;
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

    private final UserReportRepository userReportRepository;
    private final BoardReportRepository boardReportRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;


    @Override
    public UserReportResponse reportUser(User reporter, UserReportRequest req) {

        User reportedUser = userRepository.findById(req.getReportedUserId()).orElseThrow(MemberNotFoundException::new);

        if (reporter.getId() == req.getReportedUserId()) {
            throw new NotSelfReportException();
        }

        if (userReportRepository.findByReporterIdAndReportedUserId(reporter.getId(), req.getReportedUserId()) == null) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리 (ReportedUser의 User테이블 boolean 값 true 변경 ==> 신고처리)
            UserReport userReport = new UserReport(reporter.getId(), reportedUser.getId(), req.getContent());
            userReportRepository.save(userReport);

            if (userReportRepository.findByReportedUserId(req.getReportedUserId()).size() >= 1) {
                // 신고 수 10 이상일 시 true 설정
                reportedUser.setReported(true);
            }

            UserReportResponse res = new UserReportResponse(userReport.getId(), UserEditRequestDto.toDto(reportedUser), req.getContent());
            return res;
        } else {
            throw new AlreadyReportException();
        }
    }

    @Override
    public BoardReportResponse reportBoard(User reporter, BoardReportRequest req) {
        Board reportedBoard = boardRepository.findById(req.getReportedBoardId()).orElseThrow(BoardNotFoundException::new);

        if (reporter.getId() == reportedBoard.getUser().getId()){
            throw new NotSelfReportException();
        }

        if (boardReportRepository.findByReporterIdAndReportedBoardId(reporter.getId(), req.getReportedBoardId()) == null) {
            // 신고 한 적이 없다면, 테이블 생성 후 신고 처리
            BoardReport boardReport = new BoardReport(reporter.getId(), reportedBoard.getId(), req.getContent());
            boardReportRepository.save(boardReport);

            if (boardReportRepository.findByReportedBoardId(req.getReportedBoardId()).size() >= 10) {
                reportedBoard.setReported(true);
            }

            BoardReportResponse res = new BoardReportResponse(boardReport.getId(), req.getReportedBoardId(), req.getContent());
            return res;
        } else {
            throw new AlreadyReportException();
        }

    }
}
