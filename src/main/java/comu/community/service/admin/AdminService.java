package comu.community.service.admin;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.member.MemberEditRequestDto;

import java.util.List;

public interface AdminService {

    List<MemberEditRequestDto> findReportedUsers();
    MemberEditRequestDto processUnlockUser(Long id);
    List<BoardSimpleDto> findReportedBoards();
    BoardSimpleDto processUnlockBoard(Long id);

}
