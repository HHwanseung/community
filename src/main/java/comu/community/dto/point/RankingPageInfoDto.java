package comu.community.dto.point;

import comu.community.entity.point.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RankingPageInfoDto {
    private int totalPage;
    private int nowPage;
    private int numberOfElements;
    private boolean isNext;

    public RankingPageInfoDto(Page<Point> result) {
        this.totalPage = result.getTotalPages();
        this.nowPage = result.getNumber();
        this.numberOfElements = result.getNumberOfElements();
        this.isNext = result.hasNext();
    }
}
