package comu.community.entity.report;

import comu.community.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BoardReport extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reporterId;

    @Column(nullable = false)
    private Long reportedBoardId;

    @Column(nullable = false)
    private String content;

    public BoardReport(Long reporterId, Long reportedBoardId, String content) {
        this.reporterId = reporterId;
        this.reportedBoardId = reportedBoardId;
        this.content = content;
    }
}
