package comu.community.repository.board;

import comu.community.entity.board.Board;
import comu.community.entity.board.Favorite;
import comu.community.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByBoardAndUser(Board board, User user);
    List<Favorite> findAllByUser(User user);
}
