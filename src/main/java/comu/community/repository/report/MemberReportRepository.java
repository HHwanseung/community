package comu.community.repository.report;

import comu.community.entity.report.MemberReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberReportRepository extends JpaRepository<MemberReport, Long> {
    boolean existsByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);
    List<MemberReport> findByReportedUserId(Long reportedId);
    void deleteAllByReportedUserId(Long id);
}
