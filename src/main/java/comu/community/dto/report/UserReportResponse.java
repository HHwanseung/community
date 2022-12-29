package comu.community.dto.report;

import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.report.UserReportHistory;
import comu.community.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReportResponse {

    private Long id;
    private UserEditRequestDto reportedUser;
    private String content;

    public UserReportResponse toDto(UserReportHistory userReportHistory, User reportedUser) {
        return new UserReportResponse(
                userReportHistory.getId(),
                UserEditRequestDto.toDto(reportedUser),
                userReportHistory.getContent()
        );
    }

}
