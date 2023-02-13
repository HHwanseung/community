package comu.community.service.message;

import comu.community.dto.message.MessageCreateRequestDto;
import comu.community.dto.message.MessageDto;
import comu.community.entity.message.Message;
import comu.community.entity.member.Member;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.MemberNotFoundException;
import comu.community.exception.MessageNotFoundException;
import comu.community.repository.message.MessageRepository;
import comu.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Override
    public MessageDto createMessage(Member sender, MessageCreateRequestDto req) {
       Member receiver = getReceiver(req);
       Message message = getMessage(sender, req, receiver);
        return MessageDto.toDto(messageRepository.save(message));
    }

    private Member getReceiver(MessageCreateRequestDto req) {
        return memberRepository.findByNickname(req.getReceiverNickname())
                .orElseThrow(MemberNotFoundException::new);
    }

    private Message getMessage(Member sender, MessageCreateRequestDto req, Member receiver) {
        return new Message(req.getTitle(), req.getContent(), sender, receiver);
    }

    @Override
    public List<MessageDto> receiveMessages(Member member) {
        List<Message> messageList = messageRepository.findAllByReceiverAndDeletedByReceiverFalseOrderByIdDesc(member);
        List<MessageDto> messageDtoList = messageList.stream()
                .map(message -> MessageDto.toDto(message))
                .collect(Collectors.toList());
        return messageDtoList;
    }

    @Override
    public MessageDto receiveMessage(Long id, Member member) {
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        validateReceiveMessage(member, message);
        return MessageDto.toDto(message);
    }

    private void validateReceiveMessage(Member member, Message message) {
        if (message.getReceiver() != member) {
            throw new MemberNotEqualsException();
        }
        if (message.isDeletedByReceiver()) {
            throw new MessageNotFoundException();
        }
    }

    @Override
    public List<MessageDto> sendMessages(Member member) {
        List<Message> messageList = messageRepository.findAllBySenderAndDeletedBySenderFalseOrderByIdDesc(member);
        List<MessageDto> messageDtoList = messageList.stream()
                .map(message -> MessageDto.toDto(message))
                .collect(Collectors.toList());
        return messageDtoList;
    }

    @Override
    public MessageDto sendMessage(Long id, Member member) {
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        validateSendMessage(member, message);
        return MessageDto.toDto(message);
    }

    private void validateSendMessage(Member member, Message message) {
        if (message.getSender() != member) {
            throw new MemberNotEqualsException();
        }
        if (message.isDeletedByReceiver()) {
            throw new MessageNotFoundException();
        }
    }


    @Override
    public void deleteMessageByReceiver(Long id, Member member) {
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        processDeleteReceiverMessage(member, message);
        checkIsMessageDeletedBySenderAndReceiver(message);
    }

    private void processDeleteReceiverMessage(Member member, Message message) {
        if (message.getReceiver().equals(member)) {
            message.deleteByReceiver();
            return;
        }
        throw new MemberNotEqualsException();
    }

    private void checkIsMessageDeletedBySenderAndReceiver(Message message) {
        if (message.isDeletedMessage()) {
            messageRepository.delete(message);

        }
    }

    @Override
    public void deleteMessageBySender(Long id, Member member) {
        Message message = messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
        processDeleteSenderMessage(member, message);
        checkIsMessageDeletedBySenderAndReceiver(message);
    }

    private void processDeleteSenderMessage(Member member, Message message) {
        if (message.getSender().equals(member)) {
            message.deleteBySender();
            return;
        }
        throw new MemberNotEqualsException();
    }
}
