package comu.community.dto.message;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ApiOperation(value = "메세지 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateRequestDto {

    @ApiModelProperty(value = "메세지 제목", notes = "메세지 제목을 입력해주세요", required = true)
    @NotBlank(message = "메세지 제목을 입력해주세요")
    private String title;

    @ApiModelProperty(value = "메세지 내용", notes = "메세지 내용을 입력해주세요", required = true)
    @NotBlank(message = "메세지 내용을 입력해주세요")
    private String content;

    @ApiModelProperty(value = "수신자 닉네임", notes = "수신자 닉네임을 입력하세요", required = true)
    @NotNull(message = "받는 사람 닉네임을 입력해주세요")
    private String receiverNickname;

}
