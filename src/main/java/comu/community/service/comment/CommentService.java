package comu.community.service.comment;

import comu.community.dto.comment.CommentCreateRequest;
import comu.community.dto.comment.CommentDto;
import comu.community.dto.comment.CommentReadCondition;
import comu.community.entity.comment.Comment;
import comu.community.entity.user.User;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAllComments(CommentReadCondition condition);
    CommentDto createComment(CommentCreateRequest req, User user);
    void deleteComment(Long id, User user);
    void validateDeleteComment(Comment comment, User user);

}
