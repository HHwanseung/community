//package comu.community.controller.user;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import comu.community.service.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//class UserControllerTest {
//
//    @InjectMocks
//    UserController userController;
//
//    @Mock
//    UserService userService;
//    MockMvc mockMvc;
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void beforeEach() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    @DisplayName("전체 회원 조회")
//    public void findAllUsersTest() throws Exception{
//        mockMvc.perform(
//                get("/api/users"))
//                .andExpect(status().isOk());
//        verify(userService).findAllUsers();
//    }
//
//    @Test
//    @DisplayName("개별 회원 조회")
//    public void findUserTest() throws Exception {
//        //given
//        int id = 1;
//
//        //when, then
//        mockMvc.perform(
//                get("/api/users/{id}", id))
//                .andExpect(status().isOk());
//        verify(userService).findUser((long) id);
//    }
//
////    @Test
////    @DisplayName("회원 정보 수정")
////    public void updateUserInfoTest() throws Exception {
////        //given
////        int id = 1;
////        UserDto userDto = new UserDto(1L, "username", "name", "nickname");
////
////        //when, then
////        mockMvc.perform(
////                put("/api/users/{id}", id)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(userDto)))
////                .andExpect(status().isOk());
////
////        verify(userService).updateUserInfo((long) id, userDto);
////        Assertions.assertThat(userDto.getName()).isEqualTo("name");
////    }
//
////    @Test
////    @DisplayName("회원 탈퇴")
////    public void deleteUserInfoTest() throws Exception {
////        //given
////        int id = 1;
////
////        //when, then
////        mockMvc.perform(
////                delete("/api/users/{id}", id))
////                .andExpect(status().isOk());
////        verify(userService).deleteUserInfo(user);
////
////    }
//
//
//}