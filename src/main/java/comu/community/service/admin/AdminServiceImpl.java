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

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserReportRepository userReportRepository;
    private final BoardRepository boardRepository;
    private final BoardReportRepository boardReportRepository;

    @Override
    public List<UserEditRequestDto> manageReportedUser() {
        List<User> users = userRepository.findByReportedIsTrue();
        List<UserEditRequestDto> usersDto = new ArrayList<>();
        users.stream().forEach(i -> usersDto.add(new UserEditRequestDto().toDto(i)));
        return usersDto;
    }

    @Override
    public UserEditRequestDto unlockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(MemberNotEqualsException::new);
        if (!user.isReported()) {
            throw new NotReportedException();
        }
        user.setReported(false);
        userReportRepository.deleteAllByReportedUserId(id);
        return UserEditRequestDto.toDto(user);
    }

    @Override
    public List<BoardSimpleDto> manageReportedBoards() {
        List<Board> boards = boardRepository.findByReportedIsTrue();
        List<BoardSimpleDto> boardsDto = new ArrayList<>();
        boards.stream().forEach(i -> boardsDto.add(new BoardSimpleDto().toDto(i)));
        return boardsDto;
    }

    @Override
    public BoardSimpleDto unlockBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        if (!board.isReported()) {
            throw new NotReportedException();
        }
        board.setReported(false);
        boardReportRepository.deleteAllByReportedBoardId(id);
        return new BoardSimpleDto().toDto(board);
    }
}
