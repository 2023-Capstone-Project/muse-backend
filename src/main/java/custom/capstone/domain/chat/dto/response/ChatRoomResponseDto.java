package custom.capstone.domain.chat.dto.response;

import custom.capstone.domain.chat.domain.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private Long postId;
    private String roomId;
    private String sender;
    private String receiver;
    private String title;

    public ChatRoomResponseDto(final ChatRoom chatRoom) {
        this.chatRoomId = chatRoom.getId();
        this.postId = chatRoom.getPost().getId();
        this.roomId = chatRoom.getRoomId();
        this.sender = chatRoom.getSender();
        this.receiver = chatRoom.getReceiver();
        this.title = chatRoom.getPost().getTitle();
    }
}
