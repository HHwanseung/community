package comu.community.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import comu.community.dto.member.MemberSimpleNicknameResponseDto;
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
    private MemberSimpleNicknameResponseDto memberSimpleNicknameResponseDto;

    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                MemberSimpleNicknameResponseDto.toDto(comment.getMember())
        );
    }
}
