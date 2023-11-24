package custom.capstone.domain.chat.dto;


import custom.capstone.domain.chat.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private String roomId;
    private String sender;
    private String message;

    public MessageDto(final Message message) {
        this.roomId = message.getRoomId();
        this.sender = message.getSender();
        this.message = message.getMessage();
    }
}
