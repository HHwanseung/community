package comu.community.service.user;

import comu.community.dto.user.UserDto;
import comu.community.entity.user.User;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.toDto(user));
        }
        return userDtos;
    }

//    @Override
//    public UserDto findUser(Long id) {
//        return UserDto.toDto(userRepository.findById(id).orElseThrow(MemberNotFoundExceeption::new));
//    }
}
