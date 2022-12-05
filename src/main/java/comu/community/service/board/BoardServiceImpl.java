package comu.community.service.board;

import comu.community.dto.board.*;
import comu.community.entity.board.Board;
import comu.community.entity.board.Image;
import comu.community.entity.user.User;
import comu.community.exception.BoardNotFoundException;
import comu.community.exception.MemberNotEqualsException;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.user.UserRepository;
import comu.community.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;


    @Override
    public BoardCreateResponse createBoard(BoardCreateRequest req, User user) {

        List<Image> images = req.getImages().stream().map(i -> new Image(i.getOriginalFilename())).collect(Collectors.toList());
        Board board = boardRepository.save(new Board(req.getTitle(), req.getContent(), user, images));
        uploadImages(board.getImages(), req.getImages());
        return new BoardCreateResponse(board.getId(), board.getTitle(), board.getContent());
    }

    @Override
    public List<BoardSimpleDto> findAllBoards(Pageable pageable) {

        Page<Board> boards = boardRepository.findAll(pageable);
        List<BoardSimpleDto> boardSimpleDtoList = new ArrayList<>();
        boards.stream().forEach(i -> boardSimpleDtoList.add(new BoardSimpleDto().toDto(i)));
        return boardSimpleDtoList;
    }

    @Override
    public BoardResponseDto findBoard(Long id) {

        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        User user = board.getUser();
        return BoardResponseDto.toDto(board, user.getNickname());
    }

    @Override
    public BoardResponseDto updateBoard(Long id, BoardUpdateRequest req, User user) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        validateBoardOwner(user, board);

        Board.ImageUpdateResult result = board.update(req);
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
    public List<BoardSimpleDto> search(String keyword, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContaining(keyword, pageable );
        List<BoardSimpleDto> boardSimpleDtoList = boards.stream().map(i -> new BoardSimpleDto().toDto(i)).collect(Collectors.toList());
        return boardSimpleDtoList;
    }

    @Override
    public void uploadImages(List<Image> images, List<MultipartFile> fileImages) {
        IntStream.rangeClosed(0, images.size())
                .forEach(i -> fileService.upload(fileImages.get(i), images.get(i).getUniqueName()));
    }

    @Override
    public void deleteImages(List<Image> images) {
        images.forEach(i -> fileService.delete(i.getUniqueName()));

    }

    public void validateBoardOwner(User user, Board board) {
        if (!user.equals(board.getUser())) {
            throw new MemberNotEqualsException();
        }
    }
}
