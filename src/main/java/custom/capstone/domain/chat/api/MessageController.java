package custom.capstone.domain.chat.api;

import custom.capstone.domain.chat.application.ChatRoomService;
import custom.capstone.domain.chat.application.MessageService;
import custom.capstone.domain.chat.dto.MessageDto;
import custom.capstone.global.redis.RedisPublisher;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomService chatRoomService;
    private final MessageService chatMessageService;

    /**
     * websocket "/pub/chat/message" 로 들어오는 메시징을 처리한다.
     */
    @Operation(summary = "채팅저장")
    @MessageMapping("/chat/message")
    public void message(@Payload final MessageDto messageDto) {
        // 클라이언트 Topic 입장 & 대화를 위해 리스너와 연동
        chatRoomService.enterChatRoom(messageDto.getRoomId());

        // Websocket 에 발행된 메시지를 redis 로 발행
        redisPublisher.publish(chatRoomService.getTopic(messageDto.getRoomId()), messageDto);

        // DB & Redis 에 대화 저장
        chatMessageService.saveMessage(messageDto);
    }

    @Operation(summary = "대화 내역 조회")
    @GetMapping("/chat/room/{roomId}/message")
    public ResponseEntity<List<MessageDto>> loadMessage(@PathVariable("roomId") final String roomId) {
        return ResponseEntity.ok(chatMessageService.loadMessage(roomId));
    }
}
