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
    private Long reportedBoardId;
    private String content;

    public BoardReportResponse toDto(BoardReportHistory boardReportHistory) {
        return new BoardReportResponse(
                boardReportHistory.getId(),
                boardReportHistory.getReportedBoardId(),
                boardReportHistory.getContent()
        );
    }

}
