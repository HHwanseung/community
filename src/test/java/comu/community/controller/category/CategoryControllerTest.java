package comu.community.controller.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import comu.community.dto.category.CategoryCreateRequest;
import comu.community.service.category.CategoryService;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @InjectMocks
    CategoryController categoryController;

    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    @DisplayName("전체 카테고리 조회")
    void readAllTest() throws Exception {
        // given

        // when,then
        mockMvc.perform(
                get("/api/categories"))
                .andExpect(status().isOk());
        verify(categoryService).findAllCategory();
    }

    @Test
    @DisplayName("카테고리 생성")
    void createTest() throws Exception {
        // given
        CategoryCreateRequest req = new CategoryCreateRequest("category1", 1);

        // when, then
        mockMvc.perform(
                post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());

        verify(categoryService).createCategory(req);
    }

    @Test
    @DisplayName("카테고리 제거")
    void deleteTest() throws Exception {
        // given
        int id = 1;

        // when,then
        mockMvc.perform(
                delete("/api/categories/{id}", id))
                .andExpect(status().isOk());
        verify(categoryService).deleteCategory(id);
    }
}