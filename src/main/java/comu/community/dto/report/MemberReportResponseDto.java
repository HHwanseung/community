package comu.community.dto.report;

import comu.community.dto.member.MemberEditRequestDto;
import comu.community.entity.report.MemberReportHistory;
import comu.community.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberReportResponseDto {
    private Long id;
    private MemberEditRequestDto reportedUser;
    private String content;

    public MemberReportResponseDto toDto(MemberReportHistory memberReportHistory, Member reportedMember) {
        return new MemberReportResponseDto(
                memberReportHistory.getId(),
                MemberEditRequestDto.toDto(reportedMember),
                memberReportHistory.getContent()
        );
    }

}
