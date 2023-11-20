package custom.capstone.domain.chat.api;

import custom.capstone.domain.chat.application.ChatRoomService;
import custom.capstone.domain.chat.dto.request.ChatRoomSaveRequestDto;
import custom.capstone.domain.chat.dto.response.ChatRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     */
    @PostMapping("/room")
    public ChatRoomResponseDto createChatRoom(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody final ChatRoomSaveRequestDto requestDto
    ) throws IllegalAccessException {
        return chatRoomService.saveChatRoom(loginEmail, requestDto);
    }

    /**
     * 채팅방 목록 조회
     */
    @GetMapping("/rooms")
    public List<ChatRoomResponseDto> findAll() {
        return chatRoomService.findAll();
    }

    /**
     * 채팅방 단일 조회
     */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoomResponseDto getChatRoomDetail(@PathVariable String roomId) {
        return chatRoomService.findById(roomId);
    }

    /**
     * 채팅방 삭제
     */
    @DeleteMapping("/room/{roomId}")
    public HttpStatus deleteChatRoom(@PathVariable("roomId") final String roomId) {
        chatRoomService.deleteChatRoom(roomId);
        return HttpStatus.OK;
    }
}
