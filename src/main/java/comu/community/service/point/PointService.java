package comu.community.service.point;

import comu.community.dto.point.PointRankingRedisResponseDto;
import comu.community.dto.point.PointRankingWithPagingResponseDto;
import comu.community.entity.point.Point;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PointService {

    PointRankingRedisResponseDto findPointRankKingWithMysql(Integer points);
    List<PointRankingRedisResponseDto> findPointsRankingWithRedis();
    void updatePoint(String username);

}
