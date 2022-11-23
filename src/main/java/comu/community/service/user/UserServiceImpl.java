package comu.community.service.user;

import comu.community.dto.user.UserDto;
import comu.community.entity.user.Role;
import comu.community.entity.user.User;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public UserDto findUser(Long id) {
        return UserDto.toDto(userRepository.findById(id).orElseThrow(MemberNotFoundException::new));
    }

    @Override
    public UserDto updateUserInfo(Long id, UserDto updateInfo) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new MemberNotFoundException();
        });

        // 권한 처리
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getName().equals(user.getUsername())) {
            throw new MemberNotEqualsException();
        } else {
            user.setNickname(updateInfo.getNickname());
            user.setName(updateInfo.getName());
            return UserDto.toDto(user);
        }
    }

    @Override
    public void deleteUserInfo(Long id) {

        User user = userRepository.findById(id).orElseThrow(MemberNotFoundException::new);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String auth = String.valueOf(authentication.getAuthorities());
        String authByAdmin = "[" + Role.ROLE_ADMIN + "]";

        if (authentication.getName().equals(user.getUsername()) || auth.equals(authByAdmin)) {
            userRepository.deleteById(id);
        } else {
            throw new MemberNotEqualsException();
        }

    }




}
