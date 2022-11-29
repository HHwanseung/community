package comu.community.service.message;

import comu.community.dto.message.MessageCreateRequestDto;
import comu.community.dto.message.MessageDto;
import comu.community.entity.user.User;

import java.util.List;

public interface MessageService {

    MessageDto createMessage(User sender,MessageCreateRequestDto req);
    List<MessageDto> receiveMessages(User user);
    MessageDto receiveMessage(Long id, User user);
    List<MessageDto> sendMessages(User user);
    MessageDto sendMessage(Long id,User user);
    void deleteMessageByReceiver(Long id, User user);
    void deleteMessageBySender(Long id,User user);




}
