package comu.community.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReadCondition {
    @NotBlank(message = "게시글 번호를 입력해주세요")
    @PositiveOrZero(message = "올바른 게시물 번호를 입력해주세요 (0 이상)")
    private Long boardId;
}
