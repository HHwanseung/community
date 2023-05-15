package comu.community.controller.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import comu.community.dto.board.BoardCreateRequest;
import comu.community.dto.board.BoardUpdateRequest;
import comu.community.entity.board.Board;
import comu.community.entity.member.Member;
import comu.community.repository.board.BoardRepository;
import comu.community.repository.member.MemberRepository;
import comu.community.service.board.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static comu.community.factory.MemberFactory.createUserWithAdminRole;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {
    @InjectMocks
    BoardController boardController;

    @Mock
    BoardService boardService;
    @Mock
    BoardRepository boardRepository;
    @Mock
    MemberRepository memberRepository;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    @DisplayName("게시글 작성")
    public void createTest() throws Exception {
        //given
        ArgumentCaptor<BoardCreateRequest> boardCreateRequestArgumentCaptor = ArgumentCaptor.forClass(BoardCreateRequest.class);
        List<MultipartFile> images = new ArrayList<>();
        images.add(new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()));
        images.add(new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes()));
        BoardCreateRequest req = new BoardCreateRequest("title", "content", images);

        Member member = createUserWithAdminRole();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));

        //when, then
        mockMvc.perform(
                multipart("/api/boards")
                        .file("images", images.get(0).getBytes())
                        .file("images", images.get(1).getBytes())
                        .param("title", req.getTitle())
                        .param("content", req.getContent())
                        .with(requestBoardProcessor -> {
                            requestBoardProcessor.setMethod("POST");
                            return requestBoardProcessor;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("게시물 검색")
    public void searchBoardTest() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<Board> result = boardRepository.findByTitleContaining("keyword", pageable);

        // when, then
        assertThat(result).isEqualTo(null);

    }

    @Test
    @DisplayName("전체 게시물 조회 (페이징)")
    public void findAllBoardsTest() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        Page<Board> result = boardRepository.findAll(pageable);

        // when, then
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("개시물 단건 조회")
    public void findBoardTest() throws Exception {
        // given
        int id = 1;

        // when, then
        mockMvc.perform(
                        get("/api/boards/{id}", id))
                .andExpect(status().isOk());

        verify(boardService).findBoard((long) id);
    }


    @Test
    @DisplayName("게시글 수정")
    public void updateBoardTest() throws Exception {
        // given
        ArgumentCaptor<BoardUpdateRequest> boardUpdateRequestArgumentCaptor = ArgumentCaptor.forClass(BoardUpdateRequest.class);

        List<MultipartFile> addedImages = new ArrayList<>();
        addedImages.add(new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes()));
        addedImages.add(new MockMultipartFile("test2", "test2.PNG", MediaType.IMAGE_PNG_VALUE, "test2".getBytes()));

        List<Integer> deletedImages = List.of(1, 2);

        BoardUpdateRequest req = new BoardUpdateRequest("title", "content", addedImages, deletedImages);

        Member member = createUserWithAdminRole();
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), "", Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        given(memberRepository.findByUsername(authentication.getName())).willReturn(Optional.of(member));


        // when, then
        mockMvc.perform(
                        multipart("/api/boards/{id}", 1)
                                .file("addedImages", addedImages.get(0).getBytes())
                                .file("addedImages", addedImages.get(1).getBytes())
                                .param("deletedImages", String.valueOf(deletedImages.get(0)), String.valueOf(deletedImages.get(1)))
                                .param("title", req.getTitle())
                                .param("content", req.getContent())
                                .with(requestBoardProcessor -> {
                                    requestBoardProcessor.setMethod("PUT");
                                    return requestBoardProcessor;
                                })
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

}