package comu.community.entity.board;

import comu.community.entity.BaseTimeEntity;
import comu.community.entity.member.Member;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class LikeBoard extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(nullable = false)
    private boolean status; //좋아요 여부

    public LikeBoard(Board board, Member member) {
        this.board = board;
        this.member = member;
        this.status = true;
    }

    public void unLikeBoard(Board board) {
        this.status = false;
        board.setLiked(board.getLiked() -1);
    }
}
