package comu.community.controller.message;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
//
//    @InjectMocks
//    MessageController messageController;
//
//    @Mock
//    MessageService messageService;
//    MockMvc mockMvc;
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    @BeforeEach
//    public void beforeEach() {
//        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
//    }
//
//    @Test
//    @DisplayName("메세지 작성")
//    public void createMessageTest() throws Exception {
//
//        //given
//        MessageCreateRequestDto req = new MessageCreateRequestDto("타이틀","내용","유저닉네임");
//
//        //when,then
//        mockMvc.perform(
//                post("/api/messages")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(req)))
//                .andExpect(status().isCreated());
//
//        verify(messageService).createMessage(req);
//
//    }
//
//    @Test
//    @DisplayName("받은 메세지 확인")
//    public void receiveMessagesTest() throws Exception {
//        //given
//
//        //when,then
//        mockMvc.perform(
//                get("/api/messages/receiver"))
//                .andExpect(status().isOk());
//        verify(messageService).receiveMessages();
//
//    }
//
//    @Test
//    @DisplayName("받은 메세지 개별 확인")
//    public void receiveMessageTest() throws Exception {
//        //given
//        int id = 1;
//
//        //when,then
//        mockMvc.perform(
//                get("/api/messages/receiver/{id}", id))
//
//                .andExpect(status().isOk());
//        verify(messageService).receiveMessage((long) id);
//
//    }
//
//    @Test
//    @DisplayName("보낸 메세지 확인")
//    public void sendMessagesTest() throws Exception {
//
//        //given
//
//        //when,then
//        mockMvc.perform(
//                get("/api/messages/sender"))
//                .andExpect(status().isOk());
//        verify(messageService).sendMessages();
//
//    }
//
//    @Test
//    @DisplayName("보낸 메세지 개별 확인")
//    public void sendMessageTest() throws Exception {
//        //given
//        int id = 1;
//
//        //when,then
//        mockMvc.perform(
//                get("/api/messages/sender/{id}", id))
//                .andExpect(status().isOk());
//        verify(messageService).sendMessage((long) id);
//
//    }
//
////    @Test
////    @DisplayName("받은 쪽지 삭제")
////    public void deleteReceiveMessageTest() throws Exception {
////        //given
////        int id = 1;
////
////        //when,then
////        mockMvc.perform(
////                delete("/api/messages/receiver/{id}", id))
////                .andExpect(status().isOk());
////
////        verify(messageService).deleteMessageByReceiver((long) id);
////
////    }
//
//    @Test
//    @DisplayName("보낸 쪽지 삭제")
//    public void deleteSenderMessageTest() throws Exception {
//        //given
//        int id = 1;
//
//        //when,then
//
//        mockMvc.perform(
//                delete("/api/messages/sender/{id}", id))
//                .andExpect(status().isOk());
//
//        verify(messageService).deleteMessageBySender((long) id);
//    }



}