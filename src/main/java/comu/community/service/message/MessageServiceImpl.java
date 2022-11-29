package comu.community.service.message;

import comu.community.dto.message.MessageCreateRequestDto;
import comu.community.dto.message.MessageDto;
import comu.community.entity.message.Message;
import comu.community.entity.user.User;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.MemberNotFoundException;
import comu.community.exception.MessageNotFoundException;
import comu.community.repository.message.MessageRepository;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public MessageDto createMessage(User sender, MessageCreateRequestDto req) {

        User receiver = userRepository.findByNickname(req.getReceiverNickname()).orElseThrow(MemberNotEqualsException::new);
        Message message = new Message(req.getTitle(), req.getContent(), sender, receiver);
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }

    @Override
    public List<MessageDto> receiveMessages(User user) {

        List<MessageDto> messageDtoList = new ArrayList<>();
        List<Message> messageList = messageRepository.findAllByReceiverAndDeletedByReceiverFalseOrderByIdDesc(user);

        for (Message message : messageList) {
            if (!message.isDeletedByReceiver()) {
                messageDtoList.add(MessageDto.toDto(message));
            }
        }

        return messageDtoList;
    }

    @Override
    public MessageDto receiveMessage(Long id, User user) {

        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);

        if (message.getReceiver() != user) {
            throw new MemberNotEqualsException();
        }

        if (message.isDeletedByReceiver()) {
            throw new MemberNotFoundException();
        }
        return MessageDto.toDto(message);
    }

    @Override
    public List<MessageDto> sendMessages(User user) {

        List<MessageDto> messageDtoList = new ArrayList<>();
        List<Message> messageList = messageRepository.findAllBySenderAndDeletedBySenderFalseOrderByIdDesc(user);

        for (Message message : messageList) {
            if (!message.isDeletedBySender()) {
                messageDtoList.add(MessageDto.toDto(message));
            }
        }

        return messageDtoList;
    }

    @Override
    public MessageDto sendMessage(Long id, User user) {

        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);

        if (message.getSender() != user) {
            throw new MemberNotEqualsException();
        }

        if (message.isDeletedByReceiver()) {
            throw new MessageNotFoundException();
        }

        return MessageDto.toDto(message);
    }

    @Override
    public void deleteMessageByReceiver(Long id, User user) {

        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);

        if (message.getReceiver() == user) {
            message.deleteByReceiver();
        } else {
            throw new MemberNotEqualsException();
        }

        if (message.isDeletedMessage()) {
            // 수신, 송신자 둘다 삭제할 경우
            messageRepository.delete(message);
        }

    }

    @Override
    public void deleteMessageBySender(Long id,User user) {

        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);

        if (message.getSender() == user) {
            message.deleteBySender();
        } else {
            throw new MemberNotEqualsException();
        }

        if (message.isDeletedMessage()) {
            messageRepository.delete(message);
        }

    }
}
