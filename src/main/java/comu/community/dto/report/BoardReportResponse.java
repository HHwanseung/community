package comu.community.dto.report;

import comu.community.entity.report.BoardReport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardReportResponse {

    private Long id;
    private Long reportedBoardId;
    private String content;

    public BoardReportResponse toDto(BoardReport boardReport) {
        return new BoardReportResponse(
                boardReport.getId(),
                boardReport.getReportedBoardId(),
                boardReport.getContent()
        );
    }

}
