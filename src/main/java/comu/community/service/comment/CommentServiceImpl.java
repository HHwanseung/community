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

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    @Override
    public List<CommentDto> findAll(CommentReadCondition condition) {
        List<Comment> commentList = commentRepository.findByBoardId(condition.getBoardId());
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentList.stream().forEach(i -> commentDtoList.add(new CommentDto().toDto(i)));
        return commentDtoList;
    }

    @Override
    public CommentDto create(CommentCreateRequest req, User user) {
        Board board = boardRepository.findById(req.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Comment comment = new Comment(req.getContent(), user, board);
        commentRepository.save(comment);
        return new CommentDto().toDto(comment);
    }

    @Override
    public void delete(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        Board board = boardRepository.findById(comment.getBoard().getId()).orElseThrow(BoardNotFoundException::new);
        if (comment.getUser().equals(user)) {
            // 삭제 진행
            commentRepository.delete(comment);
        } else {
            throw new MemberNotEqualsException();
        }
    }
}
