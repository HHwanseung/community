package comu.community.service.admin;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.user.UserEditRequestDto;

import java.util.List;

public interface AdminService {

    List<UserEditRequestDto> findReportedUsers();
    UserEditRequestDto processUnlockUser(Long id);
    List<BoardSimpleDto> findReportedBoards();
    BoardSimpleDto processUnlockBoard(Long id);

}
