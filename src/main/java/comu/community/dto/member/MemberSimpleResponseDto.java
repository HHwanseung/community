package comu.community.dto.member;

import comu.community.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberSimpleResponseDto {
    private String username;
    private String name;

    public static MemberSimpleResponseDto toDto(Member member) {
        return new MemberSimpleResponseDto(member.getUsername(), member.getName());
    }
}
