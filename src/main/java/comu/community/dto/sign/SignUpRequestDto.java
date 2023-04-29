package comu.community.dto.sign;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
거import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "회원가입 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @ApiModelProperty(value = "아이디", notes = "아이디를 입력해주세요", required = true)
    @NotBlank(message = "아이디를 입력해 주세요.")
    private String username;

    @ApiModelProperty(value = "비밀번호", notes = "비밀번호를 입력해주세요", required = true)
    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;

    @ApiModelProperty(value = "사용자 이름", notes = "사용자 이름은 한글 또는 알파벳으로 입력해주세요.", required = true)
    @NotBlank(message = "사용자 이름을 입력해 주세요.")
    @Size(min = 2, message = "사용자 이름이 너무 짧습니다.")
    private String name;

    @ApiModelProperty(value = "닉네임 이름", notes = "닉네임은 한글 또는 알파벳으로 입력해주세요.", required = true)
    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Size(min = 2, message = "닉네임이 너무 짧습니다.")
    private String nickname;
}
