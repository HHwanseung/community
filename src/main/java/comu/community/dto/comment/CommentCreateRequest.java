package comu.community.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ApiOperation(value = "댓글 생성 요청")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

    @ApiModelProperty(value = "게시물 번호 ", notes = "게시물 번호를 입력해주세요", required = true)
    @NotNull(message = "게시물 번호를 입력해주세요")
    @Positive(message = "게시물 번호를 입력해주세요")
    private Long boardId;

    @ApiModelProperty(value = "댓글", notes = "댓글을 입력해주세요", required = true)
    @NotBlank(message = "댓글을 입력해주세요")
    private String content;


}
