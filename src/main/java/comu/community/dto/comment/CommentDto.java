package comu.community.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private int id;
    private String content;
    private UserEditRequestDto usereditRequestDto;

    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                UserEditRequestDto.toDto(comment.getUser())
        );
    }
}
