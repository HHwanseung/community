package comu.community.controller.board;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import comu.community.dto.board.BoardCreateRequest;
import comu.community.dto.board.BoardUpdateRequest;
import comu.community.entity.member.Member;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.member.MemberRepository;
import comu.community.response.Response;
import comu.community.service.board.BoardService;

import javax.validation.Valid;

@Api(value = "Board Controller", tags = "Board")
@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "게시글 생성")
    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createBoard(@Valid @ModelAttribute BoardCreateRequest req,
                           @RequestParam(value = "category", required = false) int categoryId) {
        Member member = getPrincipal();
        return Response.success(boardService.createBoard(req, categoryId, member));
    }

    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 조회합니다.")
    @GetMapping("/boards/all/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public Response findAllBoards(@ApiParam(value = "카테고리 id", required = true) @PathVariable int categoryId, @RequestParam(defaultValue = "0") Integer page) {
        return Response.success(boardService.findAllBoards(page, categoryId));
    }

    @ApiOperation(value = "게시글 단건 조회")
    @GetMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response findBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        return Response.success(boardService.findBoard(id));
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PutMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response editBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id,
                              @Valid @ModelAttribute BoardUpdateRequest req) {
        Member member = getPrincipal();
        return Response.success(boardService.editBoard(id, req, member));
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        Member member = getPrincipal();
        boardService.deleteBoard(id, member);
        return Response.success();
    }

    @ApiOperation(value = "게시글 검색")
    @GetMapping("/boards/search")
    @ResponseStatus(HttpStatus.OK)
    public Response searchBoard(String keyword,
                                @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // http://localhost:8080/api/boards/search?page=0
        return Response.success(boardService.searchBoard(keyword,pageable));
    }

    @ApiOperation(value = "게시글 좋아요")
    @PostMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response likeBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {

        Member member = getPrincipal();
        return Response.success(boardService.updateLikeOfBoard(id, member));
    }

    @ApiOperation(value = "게시글 즐겨찾기")
    @PostMapping("/boards/{id}/favorites")
    @ResponseStatus(HttpStatus.OK)
    public Response favoriteBoard(@ApiParam(value = "게시글 id", required = true) @PathVariable Long id) {
        Member member = getPrincipal();
        return Response.success(boardService.updateOfFavoriteBoard(id, member));
    }

    @ApiOperation(value = "인기글 조회")
    @GetMapping("/boards/best")
    @ResponseStatus(HttpStatus.OK)
    public Response getBestBoards(
            @PageableDefault(size = 5, sort = "liked", direction = Sort.Direction.DESC) Pageable pageable) {
        return Response.success(boardService.findBestBoards(pageable));
    }

    private Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }
}
