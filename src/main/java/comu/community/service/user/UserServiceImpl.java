package comu.community.service.user;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.user.UserDto;
import comu.community.entity.board.Favorite;
import comu.community.entity.user.Role;
import comu.community.entity.user.User;
import comu.community.exception.MemberNotEqualsException;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.board.FavoriteRepository;
import comu.community.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;


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
    public User updateUserInfo(User user, UserDto updateInfo) {
        user.editUser(updateInfo);
        return user;

    }

    @Override
    public void deleteUserInfo(User user) {

        userRepository.delete(user);
    }

    @Override
    public List<BoardSimpleDto> findFavorites(User user) {
        List<Favorite> favorites = favoriteRepository.findAllByUser(user);
        List<BoardSimpleDto> boardSimpleDtoList = new ArrayList<>();
        favorites.stream()
                .map(favorite -> boardSimpleDtoList.add(new BoardSimpleDto().toDto(favorite.getBoard())))
                .collect(Collectors.toList());
        return boardSimpleDtoList;
    }


}
