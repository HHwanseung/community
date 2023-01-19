package comu.community.dto.sign;

import comu.community.entity.member.Role;
import comu.community.entity.member.Member;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiOperation(value = "로그인 요청")
public class LoginRequestDto {

    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true)
    @NotBlank(message = "{LoginRequestDto.username.notBlank}")
    private String username;

    @ApiModelProperty(value = "비밀번호", required = true)
    @NotBlank(message = "{LoginRequestDto.password.notBlank}")
    private String password;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username,password);
    }

}
