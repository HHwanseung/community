package comu.community.repository.point;

import comu.community.entity.member.Member;
import comu.community.entity.point.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Page<Point> findAll(Pageable pageable);
}
