package comu.community.service.board;

import comu.community.dto.board.*;
import comu.community.entity.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    BoardCreateResponse createBoard(BoardCreateRequest req, int categoryId, User user);
    BoardFindAllWithPagingResponseDto findAllBoards(Integer page, int categoryId);
    BoardResponseDto findBoard(Long id);
    BoardResponseDto editBoard(Long id, BoardUpdateRequest req, User user);
    void deleteBoard(Long id, User user);
    List<BoardSimpleDto> searchBoard(String keyword, Pageable pageable);
    String updateLikeOfBoard(Long id, User user);
    String updateOfFavoriteBoard(Long id, User user);
    List<BoardSimpleDto> findBestBoards(Pageable pageable);





}
