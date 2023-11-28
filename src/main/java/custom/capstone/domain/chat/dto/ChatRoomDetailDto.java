package custom.capstone.domain.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomDetailDto {
    private ChatRoomDto chatRoom;
    private List<MessageDto> messages;

    public ChatRoomDetailDto(final ChatRoomDto chatRoom, final List<MessageDto> messages) {
        this.chatRoom = chatRoom;
        this.messages = messages;
    }
}
