package comu.community.service.board;

import comu.community.dto.board.*;
import comu.community.entity.board.Image;
import comu.community.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    BoardCreateResponse createBoard(BoardCreateRequest req, User user);
    List<BoardSimpleDto> findAllBoards(Pageable pageable);
    BoardResponseDto findBoard(Long id);
//    String updateLikeOfBoard(Long id, User user);
//    String updateOfFavoriteBoard(Long id, User user);
//    List<BoardSimpleDto> findBestBoards(Pageable pageable);
    BoardResponseDto updateBoard(Long id, BoardUpdateRequest req, User user);
    void deleteBoard(Long id, User user);
    List<BoardSimpleDto> search(String keyword, Pageable pageable);
    void  uploadImages(List<Image> images, List<MultipartFile> fileImages);
    void deleteImages(List<Image> images);





}
