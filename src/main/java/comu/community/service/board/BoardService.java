package comu.community.service.board;

import comu.community.dto.board.*;
import comu.community.entity.member.Member;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    BoardCreateResponse createBoard(BoardCreateRequest req, int categoryId, Member member);
    BoardFindAllWithPagingResponseDto findAllBoards(Integer page, int categoryId);
    BoardResponseDto findBoard(Long id);
    BoardResponseDto editBoard(Long id, BoardUpdateRequest req, Member member);
    void deleteBoard(Long id, Member member);
    List<BoardSimpleDto> searchBoard(String keyword, Pageable pageable);
    String updateLikeOfBoard(Long id, Member member);
    String updateOfFavoriteBoard(Long id, Member member);
    List<BoardSimpleDto> findBestBoards(Pageable pageable);





}
