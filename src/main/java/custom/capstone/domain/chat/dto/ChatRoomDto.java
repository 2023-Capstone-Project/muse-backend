package custom.capstone.domain.chat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import custom.capstone.domain.members.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomDto implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private Long id;
    private Long postId;
    private String roomId;
    private String sender;
    private String receiver;

    // 채팅방 생성
    public static ChatRoomDto create(final MessageRequestDto requestDto, final Member member) {
        final ChatRoomDto chatRoomDto = new ChatRoomDto();

        chatRoomDto.roomId = UUID.randomUUID().toString();
        chatRoomDto.sender = member.getNickname();
        chatRoomDto.receiver = requestDto.receiver();

        return chatRoomDto;
    }

    // 채팅방 조회
    public ChatRoomDto(final String roomId, final String sender, final String receiver) {
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setChatRoomPostId(Long postId) {
        this.postId = postId;
    }
}