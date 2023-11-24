package custom.capstone.domain.chat.api;

import custom.capstone.domain.chat.application.ChatRoomService;
import custom.capstone.domain.chat.dto.MessageRequestDto;
import custom.capstone.domain.chat.dto.MessageResponseDto;
import custom.capstone.domain.chat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    /**
     * 채팅방 생성
     */
    @PostMapping("/room")
    public MessageResponseDto createChatRoom(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody final MessageRequestDto requestDto
    ) {
        return chatRoomService.createRoom(loginEmail, requestDto);
    }

    /**
     * 채팅방 목록 조회
     */
    @GetMapping("/rooms")
    public List<MessageResponseDto> findAll(@AuthenticationPrincipal final String loginEmail) {
        return chatRoomService.findAll(loginEmail);
    }

    /**
     * 채팅방 단일 조회
     */
    @GetMapping("/room/enter/{roomId}")
    @ResponseBody
    public ChatRoomDto getChatRoomDetail(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("roomId") final String roomId
    ) {
        return chatRoomService.findRoom(loginEmail, roomId);
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
