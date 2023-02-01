package comu.community.service.admin;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.member.MemberEditRequestDto;
import comu.community.dto.member.MemberSimpleNicknameResponseDto;
import comu.community.entity.board.Board;
import comu.community.entity.member.Member;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.NotReportedException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.report.BoardReportRepository;
import comu.community.repository.report.MemberReportRepository;
import comu.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final MemberReportRepository memberReportRepository;
    private final BoardRepository boardRepository;
    private final BoardReportRepository boardReportRepository;

    @Override
    public List<MemberSimpleNicknameResponseDto> findReportedUsers() {
        List<Member> members = memberRepository.findByReportedIsTrue();
        List<MemberSimpleNicknameResponseDto> usersDto = members.stream()
                .map(user -> new MemberSimpleNicknameResponseDto().toDto(user))
                .collect(Collectors.toList());
        return usersDto;
    }

    @Override
    public MemberSimpleNicknameResponseDto processUnlockUser(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotEqualsException::new);
        validateUnlockUser(member);
        deleteUnlockUser(member, id);
        return MemberSimpleNicknameResponseDto.toDto(member);
    }

    private void validateUnlockUser(Member member) {
        if (!member.isReported()) {
            throw new NotReportedException();
        }
    }

    private void deleteUnlockUser(Member member, Long id) {
        member.unlockReport();
        memberReportRepository.deleteAllByReportedUserId(id);
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
