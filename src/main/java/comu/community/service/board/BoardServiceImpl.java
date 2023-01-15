package comu.community.service.board;

import comu.community.dto.board.*;
import comu.community.entity.board.Board;
import comu.community.entity.board.Favorite;
import comu.community.entity.board.Image;
import comu.community.entity.board.LikeBoard;
import comu.community.entity.category.Category;
import comu.community.entity.user.User;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.CategoryNotFoundException;
import comu.community.exception.FavoriteNotFoundException;
import comu.community.exception.MemberNotEqualsException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.board.FavoriteRepository;
import comu.community.repository.board.LikeBoardRepository;
import comu.community.repository.category.CategoryRepository;
import comu.community.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final static String SUCCESS_LIKE_BOARD = "좋아요 처리 완료";
    private final static String SUCCESS_UNLIKE_BOARD = "좋아요 취소 완료";
    private final static String SUCCESS_FAVORITE_BOARD = "즐겨찾기 처리 완료";
    private final static String SUCCESS_UNFAVORITE_BOARD = "즐겨찾기 취소 완료";
    private final static int RECOMMEND_SET_COUNT = 1;

    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final LikeBoardRepository likeBoardRepository;
    private final FavoriteRepository favoriteRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public BoardCreateResponse createBoard(BoardCreateRequest req, int categoryId, User user) {
        List<Image> images = req.getImages().stream().
                map(i -> new Image(i.getOriginalFilename()))
                .collect(toList());
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        Board board = boardRepository.save(new Board(req.getTitle(), req.getContent(), user, category, images));
        uploadImages(board.getImages(), req.getImages());
        return new BoardCreateResponse(board.getId(), board.getTitle(), board.getContent());
    }

    @Override
    public BoardFindAllWithPagingResponseDto findAllBoards(Integer page, int categoryId) {
        Page<Board> boards = makePageBoards(page, categoryId);
        return responsePagingBoards(boards);
    }

    private BoardFindAllWithPagingResponseDto responsePagingBoards(Page<Board> boards) {
        List<BoardSimpleDto> boardSimpleDtoList = boards.stream()
                .map(i -> new BoardSimpleDto().toDto(i))
                .collect(toList());
        return BoardFindAllWithPagingResponseDto.toDto(boardSimpleDtoList, new PageInfoDto(boards));
    }

    private Page<Board> makePageBoards(Integer page, int categoryId) {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<Board> boards = boardRepository.findAllByCategoryId(pageRequest, categoryId);
        return boards;
    }

    @Override
    public BoardResponseDto findBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        User user = board.getUser();
        return BoardResponseDto.toDto(board, user.getNickname());
    }

    @Override
    public BoardResponseDto editBoard(Long id, BoardUpdateRequest req, User user) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        validateBoardOwner(user, board);
        Board.ImageUpdatedResult result = board.update(req);
        uploadImages(result.getAddedImages(), result.getAddedImageFiles());
        deleteImages(result.getDeletedImages());
        return BoardResponseDto.toDto(board, user.getNickname());
    }

    @Override
    public void deleteBoard(Long id,User user) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        validateBoardOwner(user, board);
        boardRepository.delete(board);

    }

    @Override
    public List<BoardSimpleDto> searchBoard(String keyword, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword, pageable );
        List<BoardSimpleDto> boardSimpleDtoList = boards.stream().map(i -> new BoardSimpleDto().toDto(i)).collect(toList());
        return boardSimpleDtoList;
    }

    @Override
    public String updateLikeOfBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        if (!hasLikeBoard(board,user)) {
            board.increaseLikeCount();
            return createLikeBoard(board, user);
        }
        board.decreaseLikeCount();
        return removeLikeBoard(board,user);

    }

    @Override
    public String updateOfFavoriteBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        if (!hasFavoriteBoard(board,user)) {
            board.increaseFavoriteCount();
            return createFavoriteBoard(board,user);
        }
        board.decreaseFavoriteCount();
        return removeFavoriteBoard(board,user);
    }

    @Override
    public List<BoardSimpleDto> findBestBoards(Pageable pageable) {
        Page<Board> boards = boardRepository.findByLikedGreaterThanEqual(pageable, RECOMMEND_SET_COUNT);
        List<BoardSimpleDto> boardSimpleDtoList = new ArrayList<>();
        boards.stream().forEach(i -> boardSimpleDtoList.add(new BoardSimpleDto().toDto(i)));
        return boardSimpleDtoList;
    }

    private void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.range(0, images.size())
                .forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
    }

    private void deleteImages(List<Image> images) {
        images.forEach(i -> fileService.delete(i.getUniqueName()));
    }

    public void validateBoardOwner(User user, Board board) {
        if (!user.equals(board.getUser())) {
            throw new MemberNotEqualsException();
        }
    }

    public String createLikeBoard(Board board, User user) {
        LikeBoard likeBoard = new LikeBoard(board, user); // true 처리
        likeBoardRepository.save(likeBoard);
        return SUCCESS_LIKE_BOARD;
    }

    public String removeLikeBoard(Board board, User user) {
        LikeBoard likeBoard = likeBoardRepository.findByBoardAndUser(board, user).orElseThrow(() -> {
            throw new IllegalArgumentException("'좋아요' 기록을 찾을 수 없습니다.");
        });
        likeBoardRepository.delete(likeBoard);
        return SUCCESS_UNLIKE_BOARD;
    }

    public boolean hasLikeBoard(Board board, User user) {
        return likeBoardRepository.findByBoardAndUser(board, user).isPresent();
    }

    public String createFavoriteBoard(Board board, User user) {
        Favorite favorite = new Favorite(board, user); // true 처리
        favoriteRepository.save(favorite);
        return SUCCESS_FAVORITE_BOARD;
    }

    public String removeFavoriteBoard(Board board, User user) {
        Favorite favorite = favoriteRepository.findByBoardAndUser(board, user)
                .orElseThrow(FavoriteNotFoundException::new);
        favoriteRepository.delete(favorite);
        return SUCCESS_UNFAVORITE_BOARD;
    }

    public boolean hasFavoriteBoard(Board board, User user) {
        return favoriteRepository.findByBoardAndUser(board, user).isPresent();
    }
}
