package custom.capstone.domain.chat.api;

import custom.capstone.domain.chat.application.ChatRoomService;
import custom.capstone.domain.chat.application.MessageService;
import custom.capstone.domain.chat.dto.*;
import io.swagger.v3.oas.annotations.Operation;
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
    private final MessageService messageService;

    @Operation(summary = "채팅방 생성")
    @PostMapping("/room")
    public MessageResponseDto createChatRoom(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody final MessageRequestDto requestDto
    ) {
        return chatRoomService.createRoom(loginEmail, requestDto);
    }

    @Operation(summary = "채팅방 목록 조회")
    @GetMapping("/rooms")
    public List<MessageResponseDto> findAll(@AuthenticationPrincipal final String loginEmail) {
        return chatRoomService.findAll(loginEmail);
    }

    @Operation(summary = "채팅방 단일 조회")
    @GetMapping("/room/enter/{roomId}")
    @ResponseBody
    public ChatRoomDetailDto getChatRoomDetail(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("roomId") final String roomId
    ) {

        // 채팅방 상세 정보 조회
        final ChatRoomDto chatRoomDto = chatRoomService.findRoom(loginEmail, roomId);

        // 채팅 메시지 내역 조회
        final List<MessageDto> messageList = messageService.loadMessage(roomId);

        // ChatRoomDetailDto에 채팅방 정보와 메시지 내역을 설정하여 반환
        return new ChatRoomDetailDto(chatRoomDto, messageList);
    }

    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/room/{roomId}")
    public HttpStatus deleteChatRoom(@PathVariable("roomId") final String roomId) {
        chatRoomService.deleteChatRoom(roomId);
        return HttpStatus.OK;
    }
}
