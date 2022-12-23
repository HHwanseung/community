package comu.community.repository.report;

import comu.community.entity.report.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    UserReport findByReporterIdAndReportedUserId(Long reportedId, Long reportedUserId);
    List<UserReport> findByReportedUserId(Long reportedId);
    void deleteAllByReportedUserId(Long id);


}
