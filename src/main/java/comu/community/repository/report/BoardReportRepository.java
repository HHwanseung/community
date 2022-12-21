package comu.community.repository.report;

import comu.community.entity.report.BoardReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardReportRepository extends JpaRepository<BoardReport, Long> {

    BoardReport findByReporterIdAndReportedBoardId(Long reporterId, Long reportedBoardId);
    List<BoardReport> findByReportedBoardId(Long reportedBoardId);

    void deleteAllByReportedBoardId(Long id);

}
