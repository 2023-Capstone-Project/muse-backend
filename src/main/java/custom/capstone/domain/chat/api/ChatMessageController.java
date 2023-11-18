package custom.capstone.domain.chat.api;

import custom.capstone.domain.chat.application.ChatMessageService;
import custom.capstone.domain.chat.application.ChatRoomService;
import custom.capstone.domain.chat.dto.request.ChatMessageSaveRequestDto;
import custom.capstone.domain.chat.dto.response.ChatMessageResponseDto;
import custom.capstone.global.service.redis.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@Controller
public class ChatMessageController {

    private final RedisPublisher redisPublisher;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    /**
     * 채팅 저장
     *
     * websocket "/pub/chat/message" 로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(final ChatMessageSaveRequestDto requestDto) {
        // 클라이언트 Topic 입장 & 대화를 위해 리스너와 연동
        chatRoomService.enterChatRoom(requestDto.roomId());

        // WebSocket 에 발행된 메시지를 redis 로 발행 -> 해당 Topic 을 구독한 클라이언트에게 메시지가 전송된다.
        redisPublisher.publish(chatRoomService.getTopic(requestDto.roomId()), requestDto);

        // DB & Redis 에 대화 저장
        chatMessageService.saveChatMessage(requestDto);
    }

    /**
     * 채팅 조회
     */
    @GetMapping("/chat/room/{roomId}/message")
    public List<ChatMessageResponseDto> loadChatMessage(@PathVariable("roomId") final String roomId) {
        return chatMessageService.loadChatMessage(roomId);
    }
}
