package comu.community.service.admin;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.member.MemberEditRequestDto;
import comu.community.dto.member.MemberSimpleNicknameResponseDto;

import java.util.List;

public interface AdminService {

    List<MemberSimpleNicknameResponseDto> findReportedUsers();
    MemberSimpleNicknameResponseDto processUnlockUser(Long id);
    List<BoardSimpleDto> findReportedBoards();
    BoardSimpleDto processUnlockBoard(Long id);

}
