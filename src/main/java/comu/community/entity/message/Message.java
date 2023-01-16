package comu.community.entity.message;

import comu.community.entity.BaseTimeEntity;
import comu.community.entity.member.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean deletedBySender;

    @Column(nullable = false)
    private boolean deletedByReceiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Member receiver;

    public Message(String title, String content, Member sender, Member receiver) {
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.deletedBySender = this.deletedByReceiver = false;
    }

    public void deleteBySender() {
        this.deletedBySender = true;
    }

    public void deleteByReceiver() {
        this.deletedByReceiver = true;
    }

    public boolean isDeletedMessage() {
        return isDeletedByReceiver() && isDeletedBySender();
        // 둘다 삭제되면 true 리턴
    }


}
