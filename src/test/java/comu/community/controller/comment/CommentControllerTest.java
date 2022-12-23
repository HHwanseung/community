package comu.community.controller.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import comu.community.dto.comment.CommentCreateRequest;
import comu.community.service.comment.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class CommentControllerTest {
//    @InjectMocks
//    CommentController commentController;
//
//    @Mock
//    CommentService commentService;
//    MockMvc mockMvc;
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void beforeEach() {
//        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
//    }
//
//    @Test
//    @DisplayName("댓글 작성")
//    public void createCommentTest() throws Exception {
//        // given
//        CommentCreateRequest req = new CommentCreateRequest("content", 1L);
//
//        //when then
//        mockMvc.perform(
//                post("/api/comments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated());
//
//        verify(commentService).create(req);
//
//    }
//
//}