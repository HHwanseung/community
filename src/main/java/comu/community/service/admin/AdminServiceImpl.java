package comu.community.service.admin;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.board.Board;
import comu.community.entity.user.User;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.NotReportedException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.report.BoardReportRepository;
import comu.community.repository.report.UserReportRepository;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final BoardRepository boardRepository;
    private final BoardReportRepository boardReportRepository;

    @Override
    public List<UserEditRequestDto> findReportedUsers() {
        List<User> users = userRepository.findByReportedIsTrue();
        List<UserEditRequestDto> usersDto = users.stream()
                .map(user -> new UserEditRequestDto().toDto(user))
                .collect(Collectors.toList());
        return usersDto;
    }

    @Override
    public UserEditRequestDto processUnlockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(MemberNotEqualsException::new);
        validateUnlockUser(user);
        deleteUnlockUser(user, id);
        return UserEditRequestDto.toDto(user);
    }

    private void validateUnlockUser(User user) {
        if (!user.isReported()) {
            throw new NotReportedException();
        }
    }

    private void deleteUnlockUser(User user, Long id) {
        user.unlockReport();
        userReportRepository.deleteAllByReportedUserId(id);
    }

    @Override
    public List<BoardSimpleDto> findReportedBoards() {
        List<Board> boards = boardRepository.findByReportedIsTrue();
        List<BoardSimpleDto> boardsDto = boards.stream()
                .map(board -> new BoardSimpleDto().toDto(board))
                .collect(Collectors.toList());
        return boardsDto;
    }

    @Override
    public BoardSimpleDto processUnlockBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        validateUnlockBoard(board);
        deleteUnlockBoard(board, id);
        return new BoardSimpleDto().toDto(board);
    }

    private void deleteUnlockBoard(Board board, Long id) {
        board.unReportedBoard();
        boardReportRepository.deleteAllByReportedBoardId(id);
    }

    private void validateUnlockBoard(Board board) {
        if (!board.isReported()) {
            throw new NotReportedException();
        }
    }
}
