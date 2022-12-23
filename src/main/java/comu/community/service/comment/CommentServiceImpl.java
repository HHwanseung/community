package comu.community.service.comment;

import comu.community.dto.comment.CommentCreateRequest;
import comu.community.dto.comment.CommentDto;
import comu.community.dto.comment.CommentReadCondition;
import comu.community.entity.board.Board;
import comu.community.entity.comment.Comment;
import comu.community.entity.user.User;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.CommentNotFoundException;
import comu.community.exception.MemberNotEqualsException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.commnet.CommentRepository;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public List<CommentDto> findAllComments(CommentReadCondition condition) {
        List<Comment> comments = commentRepository.findByBoardId(condition.getBoardId());
        List<CommentDto> commentsDto = comments.stream()
                .map(comment -> new CommentDto().toDto(comment))
                .collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public CommentDto createComment(CommentCreateRequest req, User user) {
        Board board = boardRepository.findById(req.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Comment comment = new Comment(req.getContent(), user, board);
        commentRepository.save(comment);
        return new CommentDto().toDto(comment);
    }

    @Override
    public void deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        validateDeleteComment(comment, user);
        commentRepository.delete(comment);
    }

    @Override
    public void validateDeleteComment(Comment comment, User user) {
        if (!comment.isOwnComment(user)) {
            throw new MemberNotEqualsException();
        }
    }
}
