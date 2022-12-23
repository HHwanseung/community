package comu.community.dto.report;

import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.report.UserReport;
import comu.community.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReportResponse {

    private Long id;
    private UserEditRequestDto reportUser;
    private String content;

    public UserReportResponse toDto(UserReport userReport, User reportedUser) {
        return new UserReportResponse(
                userReport.getId(),
                UserEditRequestDto.toDto(reportedUser),
                userReport.getContent()
        );
    }

}
