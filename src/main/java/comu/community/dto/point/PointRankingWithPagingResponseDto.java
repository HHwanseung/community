package comu.community.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointRankingWithPagingResponseDto {
    private List<PointRankingSimpleDto> ranking;
    private RankingPageInfoDto rankingPageInfoDto;

    public static PointRankingWithPagingResponseDto toDto(List<PointRankingSimpleDto> ranking, RankingPageInfoDto rankingPageInfoDto) {
        return new PointRankingWithPagingResponseDto(ranking, rankingPageInfoDto);
    }
}
