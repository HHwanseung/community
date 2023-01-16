package comu.community.entity.report;

import comu.community.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class MemberReportHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reporterId;

    @Column(nullable = false)
    private Long reportedUserId;

    @Column(nullable = false)
    private String content;

    public MemberReportHistory(Long reporterId, Long reportedUserId, String content) {
        this.reporterId = reporterId;
        this.reportedUserId = reportedUserId;
        this.content = content;
    }

}
