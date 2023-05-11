package comu.community.dto.report;

import comu.community.dto.member.MemberEditRequestDto;
import comu.community.entity.report.MemberReport;
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

    public MemberReportResponseDto toDto(MemberReport memberReport, Member reportedMember) {
        return new MemberReportResponseDto(
                memberReport.getId(),
                MemberEditRequestDto.toDto(reportedMember),
                memberReport.getContent()
        );
    }

}
