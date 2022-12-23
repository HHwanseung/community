package comu.community.entity.report;

import comu.community.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reporterId;

    @Column(nullable = false)
    private Long reportedUserId;

    @Column(nullable = false)
    private String content;

    public UserReport(Long reporterId, Long reportedUserId, String content) {
        this.reporterId = reporterId;
        this.reportedUserId = reportedUserId;
        this.content = content;
    }

}
