package comu.community.dto.user;

import comu.community.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String username; // 로그인 아이디
    private String name; // 유저 실명
    private String nickname; // 유저 닉네임

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getName(), user.getNickname());
    }


}
