package comu.community.dto.board;

import comu.community.entity.board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSimpleDto {

    private Long id;
    private String title;
    private String nickname;
    private int liked;
    private int favorited;

    public BoardSimpleDto toDto(Board board) {
        return new BoardSimpleDto(board.getId(), board.getTitle(), board.getUser().getUsername(), board.getLiked(), board.getFavorited());
    }
}
