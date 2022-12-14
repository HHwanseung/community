package comu.community.service.comment;

import comu.community.dto.comment.CommentCreateRequest;
import comu.community.dto.comment.CommentDto;
import comu.community.dto.comment.CommentReadCondition;
import comu.community.entity.user.User;

import java.util.List;

public interface CommentService {

    List<CommentDto> findAll(CommentReadCondition condition);
    CommentDto create(CommentCreateRequest req, User user);
    void delete(Long id, User user);

}
