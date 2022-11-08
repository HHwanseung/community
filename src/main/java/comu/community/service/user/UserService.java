package comu.community.service.user;

import comu.community.dto.user.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAllUsers();

//    UserDto findUser(Long id);


}
