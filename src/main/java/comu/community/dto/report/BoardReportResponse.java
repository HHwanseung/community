package comu.community.dto.report;

import comu.community.entity.report.BoardReportHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardReportResponse {

    private Long id;
    private Long reportBoardId;
    private String content;

    public BoardReportResponse toDto(BoardReportHistory boardReport) {

        return new BoardReportResponse(
                boardReport.getId(),
                boardReport.getReportedBoardId(),
                boardReport.getContent()
        );
    }

}
