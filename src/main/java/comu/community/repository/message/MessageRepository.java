package comu.community.repository.message;

import comu.community.entity.message.Message;
import comu.community.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByReceiverAndDeletedByReceiverFalseOrderByIdDesc(Member member);
    List<Message> findAllBySenderAndDeletedBySenderFalseOrderByIdDesc(Member member);
}
