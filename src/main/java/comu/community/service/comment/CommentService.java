package comu.community.service.comment;

import comu.community.dto.comment.CommentCreateRequest;
import comu.community.dto.comment.CommentDto;
import comu.community.dto.comment.CommentReadCondition;
import comu.community.entity.comment.Comment;
import comu.community.entity.member.Member;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllComments(CommentReadCondition condition);
    CommentDto createComment(CommentCreateRequest req, Member member);
    void deleteComment(Long id, Member member);
    void validateDeleteComment(Comment comment, Member member);

}
