package comu.community.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import comu.community.dto.sign.LoginRequestDto;
import comu.community.dto.sign.SignUpRequestDto;
import comu.community.dto.sign.TokenResponseDto;
import comu.community.service.auth.AuthService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    AuthController authController;
    @Mock
    AuthService authService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @DisplayName("회원가입")
    @Test
    void signUp() throws Exception {
        //given
        SignUpRequestDto req = new SignUpRequestDto("username","123","name","nickname");

        //when,then
        mockMvc.perform(
                post("/api/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
        verify(authService).signup(req);
    }

    @DisplayName("로그인")
    @Test
    void signin() throws Exception {
        //given
        LoginRequestDto req = new LoginRequestDto("username","123");
        given(authService.signIn(req)).willReturn(new TokenResponseDto("access","refresh"));

        //when,then
        mockMvc.perform(
                post("/api/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.data.accessToken").value("access"))
                .andExpect(jsonPath("$.result.data.refreshToken").value("refresh"));
        verify(authService).signIn(req);
    }

}
