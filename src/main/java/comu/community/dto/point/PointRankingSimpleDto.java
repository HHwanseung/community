package comu.community.dto.point;

import comu.community.entity.point.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointRankingSimpleDto {
    private Long point_id;
    private String username;
    private int point;

    public PointRankingSimpleDto toDto(Point point) {
        return new PointRankingSimpleDto(point.getId(), point.getMember().getUsername(), point.getPoint());
    }
}
