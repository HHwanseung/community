package comu.community.repository.report;

import comu.community.entity.report.BoardReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardReportRepository extends JpaRepository<BoardReportHistory, Long> {

    boolean existsByReporterIdAndReportedBoardId(Long reporterId, Long reportedBoardId);

    List<BoardReportHistory> findByReportedBoardId(Long reportedBoardId);

    void deleteAllByReportedBoardId(Long id);

}
