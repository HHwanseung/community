package comu.community.service.point;

import comu.community.config.constant.Constant;
import comu.community.dto.point.PointRankingRedisResponseDto;
import comu.community.dto.point.PointRankingSimpleDto;
import comu.community.dto.point.PointRankingWithPagingResponseDto;
import comu.community.dto.point.RankingPageInfoDto;
import comu.community.entity.point.Point;
import comu.community.repository.point.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PointServiceImpl implements PointService {

    private final static String RANKING_KEY = Constant.REDIS_RANKING_KEY;
    private final RedisTemplate redisTemplate;
    private final PointRepository pointRepository;

    @Override
    public PointRankingWithPagingResponseDto findPointRankKingWithMysql(Integer page) {
        Page<Point> points = makePointRankingPages(page);
        return responseRankPaging(points);
    }

    private PointRankingWithPagingResponseDto responseRankPaging(Page<Point> points) {
        List<PointRankingSimpleDto> pointRanking = points.stream().map(point -> new PointRankingSimpleDto().toDto(point)).collect(Collectors.toList());
        return PointRankingWithPagingResponseDto.toDto(pointRanking, new RankingPageInfoDto(points));
    }

    private Page<Point> makePointRankingPages(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("point").descending());
        return pointRepository.findAll(pageRequest);
    }

    @Override
    public List<PointRankingRedisResponseDto> findPointsRankingWithRedis() {
        ZSetOperations<String, String> stringStringZSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> typedTuples = stringStringZSetOperations.reverseRangeWithScores(RANKING_KEY, 0, 10);
        List<PointRankingRedisResponseDto> result = typedTuples.stream()
                .map(i -> new PointRankingRedisResponseDto().toDto(i))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public void updatePoint(String username) {
        ZSetOperations<String, String> zSetOperation = redisTemplate.opsForZSet();
        zSetOperation.incrementScore(RANKING_KEY, username, 5);
    }
}
