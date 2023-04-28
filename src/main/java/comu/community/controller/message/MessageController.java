package comu.community.controller.message;

import comu.community.dto.message.MessageCreateRequestDto;
import comu.community.entity.member.Member;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    @ApiOperation(value = "메세지 작성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages")
    public Response createMessage(@Valid @RequestBody MessageCreateRequestDto req) {
        Member sender = getPrincipal();
        return Response.success(messageService.createMessage(sender,req));
    }

    @ApiOperation(value = "받은 메세지 전부 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/receiver")
    public Response receiveMessages() {
        Member member = getPrincipal();
        return Response.success(messageService.receiveMessages(member));
    }

    @ApiOperation(value = "받은 메세지 한 개 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/receiver/{id}")
    public Response receiveMessage(@PathVariable Long id) {
        Member member = getPrincipal();
        return Response.success(messageService.receiveMessage(id, member));
    }

    @ApiOperation(value = "보낸 메세지 전부 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sender")
    public Response sendMessages() {
        Member member = getPrincipal();
        return Response.success(messageService.sendMessages(member));
    }

    @ApiOperation(value = "보낸 메세지 한 개 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sender/{id}")
    public Response sendMessage(@PathVariable Long id) {
        Member member = getPrincipal();
        return Response.success(messageService.sendMessage(id, member));
    }

    @ApiOperation(value = "받은 쪽지 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/receiver/{id}")
    public Response deleteReceiveMessage(@ApiParam(value = "쪽지 id", required = true) @PathVariable Long id) {
        Member member = getPrincipal();
        messageService.deleteMessageByReceiver(id, member);
        return Response.success();
    }

    @ApiOperation(value = "보낸 쪽지 삭제")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/sender/{id}")
    public Response deleteSenderMessage(@ApiParam(value = "쪽지 id", required = true) @PathVariable Long id) {
        Member member = getPrincipal();
        messageService.deleteMessageBySender(id, member);
        return Response.success();
    }

    public Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

}
