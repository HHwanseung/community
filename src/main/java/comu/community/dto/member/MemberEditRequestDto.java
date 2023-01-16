package comu.community.dto.member;

import comu.community.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberEditRequestDto {

    private String name; // 유저 실명
    private String nickname; // 유저 닉네임

    public static MemberEditRequestDto toDto(Member member) {
        return new MemberEditRequestDto(member.getName(), member.getNickname());
    }


}
