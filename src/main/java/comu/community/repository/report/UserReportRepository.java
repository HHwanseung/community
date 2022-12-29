package comu.community.repository.report;

import comu.community.entity.report.UserReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReportHistory, Long> {
    boolean existsByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);
    List<UserReportHistory> findByReportedUserId(Long reportedId);
    void deleteAllByReportedUserId(Long id);


}
