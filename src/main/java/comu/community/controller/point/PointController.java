package comu.community.controller.point;

import comu.community.response.Response;
import comu.community.service.point.PointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "Comment Controller", tags = "Comment ")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PointController {
    private final PointService pointService;

    @ApiOperation(value = "포인트 랭킹 조회", notes = "전체 유저의 포인트 랭킹을 조회합니다.")
    @GetMapping("/points")
    @ResponseStatus(HttpStatus.OK)
    public Response findPointsRanking() {
        return Response.success(pointService.findPointsRankingWithRedis());
    }

    @ApiOperation(value = "포인트 업데이트", notes = "유저의 포인트를 업데이트 합니다.")
    @PutMapping("/points/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Response updatePoint(@PathVariable("username") String username) {
        pointService.updatePoint(username);
        return Response.success();
    }
}
