package comu.community.dto.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardFindAllWithPagingResponseDto {
    private List<BoardSimpleDto> boards;
    private PageInfoDto pageInfoDto;

    public static BoardFindAllWithPagingResponseDto toDto(List<BoardSimpleDto> boards, PageInfoDto pageInfoDto) {
        return new BoardFindAllWithPagingResponseDto(boards, pageInfoDto);
    }
}