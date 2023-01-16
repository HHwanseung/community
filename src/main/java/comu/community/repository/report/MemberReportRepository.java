package comu.community.repository.report;

import comu.community.entity.report.MemberReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberReportRepository extends JpaRepository<MemberReportHistory, Long> {
    boolean existsByReporterIdAndReportedUserId(Long reporterId, Long reportedUserId);

    List<MemberReportHistory> findByReportedUserId(Long reportedId);

    void deleteAllByReportedUserId(Long id);


}
