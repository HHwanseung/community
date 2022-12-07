package comu.community.dto.board;

import comu.community.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String writer_nickname;
    private String title;
    private String content;
    private List<ImageDto> images;

    public static BoardResponseDto toDto(Board board, String writer_nickname) {
        return new BoardResponseDto(
                board.getId(),
                writer_nickname,
                board.getTitle(),
                board.getContent(),
                board.getImages().stream().map(i -> ImageDto.toDto(i)).collect(toList())
        );
    }

}
