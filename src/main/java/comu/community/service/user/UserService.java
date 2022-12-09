package comu.community.service.user;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.user.UserDto;
import comu.community.entity.user.User;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers();
    UserDto findUser(Long id);
    User updateUserInfo(User user, UserDto updateInfo);
    void deleteUserInfo(User user);
    List<BoardSimpleDto> findFavorites(User user);



}
