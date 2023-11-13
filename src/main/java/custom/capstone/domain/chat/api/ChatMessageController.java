package custom.capstone.domain.chat.api;

import custom.capstone.domain.chat.dto.request.ChatMessageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessageSendingOperations sendingOperations;

    /**
     * 메시지 전송
     *
     * - 메시지 발생: /pub/api/chat
     * - 구독     : /sub/chat/{chatRoomId}
     */
    @MessageMapping("/message")
    public void sendMessage(@Payload final ChatMessageRequestDto requestDto
    ) {
        sendingOperations.convertAndSend("/sub/chat/" + requestDto.channelTopic(), requestDto.message());
    }
}
