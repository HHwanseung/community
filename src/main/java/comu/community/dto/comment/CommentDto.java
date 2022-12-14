package comu.community.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import comu.community.dto.user.UserDto;
import comu.community.entity.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    private String content;
    private UserDto userDto;

    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                UserDto.toDto(comment.getUser())
        );
    }
}
