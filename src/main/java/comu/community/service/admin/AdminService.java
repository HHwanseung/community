package comu.community.service.admin;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.user.UserEditRequestDto;

import java.util.List;

public interface AdminService {

    List<UserEditRequestDto> manageReportedUser();
    UserEditRequestDto unlockUser(Long id);
    List<BoardSimpleDto> manageReportedBoards();
    BoardSimpleDto unlockBoard(Long id);

}
