package comu.community.contoller.message;

import comu.community.dto.message.MessageCreateRequestDto;
import comu.community.entity.user.User;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.user.UserRepository;
import comu.community.response.Response;
import comu.community.service.message.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Messages Controller", tags = "Messages")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;
    private final UserRepository userRepository;

    @ApiOperation(value = "메세지 작성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages")
    public Response createMessage(@Valid @RequestBody MessageCreateRequestDto req) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User sender = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotEqualsException::new);
        return Response.success(messageService.createMessage(sender,req));
    }

    @ApiOperation(value = "받은 메세지 전부 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/receiver")
    public Response receiveMessages() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        return Response.success(messageService.receiveMessages(user));
    }

    @ApiOperation(value = "받은 메세지 한 개 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/receiver/{id}")
    public Response receiveMessage(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        return Response.success(messageService.receiveMessage(id,user));
    }

    @ApiOperation(value = "보낸 메세지 전부 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sender")
    public Response sendMessages() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        return Response.success(messageService.sendMessages(user));
    }

    @ApiOperation(value = "보낸 메세지 한 개 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sender/{id}")
    public Response sendMessage(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user =userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        return Response.success(messageService.sendMessage(id,user));
    }

    @ApiOperation(value = "받은 쪽지 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/receiver/{id}")
    public Response deleteReceiveMessage(@ApiParam(value = "쪽지 id", required = true) @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        messageService.deleteMessageByReceiver(id,user);
        return Response.success();
    }

    @ApiOperation(value = "보낸 쪽지 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/sender/{id}")
    public Response deleteSenderMessage(@ApiParam(value = "쪽지 id", required = true) @PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotEqualsException::new);
        messageService.deleteMessageBySender(id,user);
        return Response.success();
    }

}
