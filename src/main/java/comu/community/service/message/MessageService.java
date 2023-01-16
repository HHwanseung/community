package comu.community.service.message;

import comu.community.dto.message.MessageCreateRequestDto;
import comu.community.dto.message.MessageDto;
import comu.community.entity.member.Member;

import java.util.List;

public interface MessageService {

    MessageDto createMessage(Member sender, MessageCreateRequestDto req);
    List<MessageDto> receiveMessages(Member member);
    MessageDto receiveMessage(Long id, Member member);
    List<MessageDto> sendMessages(Member member);
    MessageDto sendMessage(Long id, Member member);
    void deleteMessageByReceiver(Long id, Member member);
    void deleteMessageBySender(Long id, Member member);




}
