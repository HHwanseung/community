package comu.community.controller.comment;

import comu.community.dto.comment.CommentCreateRequest;
import comu.community.dto.comment.CommentReadCondition;
import comu.community.entity.member.Member;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.member.MemberRepository;
import comu.community.response.Response;
import comu.community.service.comment.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Comment Controller", tags = "Comment")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "댓글 목록 조회")
    @GetMapping("/comments")
    @ResponseStatus(HttpStatus.OK)
    public Response findAll(@Valid CommentReadCondition condition) {
        return Response.success(commentService.findAllComments(condition));
    }

    @ApiOperation(value = "댓글 작성")
    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody CommentCreateRequest req) {
        Member member = getPrincipal();
        return Response.success(commentService.createComment(req, member));
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response delete(@ApiParam(value = "댓글 id", required = true) @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName()).orElseThrow(MemberNotFoundException::new);
        commentService.deleteComment(id, member);
        return Response.success();
    }

    public Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

}
