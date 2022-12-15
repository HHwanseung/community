package comu.community.service.user;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.user.User;

import java.util.List;

public interface UserService {

    List<UserEditRequestDto> findAllUsers();
    UserEditRequestDto findUser(Long id);
    User updateUserInfo(User user, UserEditRequestDto updateInfo);
    void deleteUserInfo(User user);
    List<BoardSimpleDto> findFavorites(User user);



}
