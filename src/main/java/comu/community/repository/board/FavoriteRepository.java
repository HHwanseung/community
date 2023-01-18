package comu.community.repository.board;

import comu.community.entity.board.Board;
import comu.community.entity.board.Favorite;
import comu.community.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByBoardAndMember(Board board, Member member);
    List<Favorite> findAllByMember(Member member);
}
