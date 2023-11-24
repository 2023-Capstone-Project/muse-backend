package custom.capstone.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import custom.capstone.domain.chat.domain.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageResponseDto {
    private Long id;
    private String sender;
    private String roomId;
    private String receiver;
    private Long postId;
    private String message;
    private LocalDateTime createdAt;

    public MessageResponseDto(final ChatRoom chatRoom) {
        this.sender = chatRoom.getSender();
        this.roomId = chatRoom.getRoomId();
        this.receiver = chatRoom.getReceiver();
        this.postId = chatRoom.getPost().getId();
    }

    public MessageResponseDto(final Long id, final String roomId, final String sender, final String receiver) {
        this.id = id;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setLatestMessageContent(final String message) {
        this.message = message;
    }

    public void setLatestMessageCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}