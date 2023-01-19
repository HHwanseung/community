package comu.community.service.comment;

import comu.community.dto.comment.CommentCreateRequest;
import comu.community.dto.comment.CommentDto;
import comu.community.dto.comment.CommentReadCondition;
import comu.community.entity.board.Board;
import comu.community.entity.comment.Comment;
import comu.community.entity.member.Member;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.CommentNotFoundException;
import comu.community.exception.MemberNotEqualsException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.commnet.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
    public CommentDto createComment(CommentCreateRequest req, Member member) {
        Board board = boardRepository.findById(req.getBoardId()).orElseThrow(BoardNotFoundException::new);
        Comment comment = new Comment(req.getContent(), member, board);
        commentRepository.save(comment);
        return new CommentDto().toDto(comment);
    }

    @Override
    public void deleteComment(Long id, Member member) {
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        validateDeleteComment(comment, member);
        commentRepository.delete(comment);
    }

    @Override
    public void validateDeleteComment(Comment comment, Member member) {
        if (!comment.isOwnComment(member)) {
            throw new MemberNotEqualsException();
        }
    }
}
