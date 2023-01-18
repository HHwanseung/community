package comu.community.repository.board;

import comu.community.entity.board.Board;
import comu.community.entity.board.LikeBoard;
import comu.community.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {
    Optional<LikeBoard> findByBoardAndMember(Board board, Member member);

}
