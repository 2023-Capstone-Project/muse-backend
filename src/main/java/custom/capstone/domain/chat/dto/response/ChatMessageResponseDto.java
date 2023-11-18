package custom.capstone.domain.chat.dto.response;

import custom.capstone.domain.chat.domain.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendAt;

    public ChatMessageResponseDto(final ChatMessage chatMessage) {
        this.roomId = chatMessage.getRoomId();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.sendAt = chatMessage.getCreatedAt();
    }
}
