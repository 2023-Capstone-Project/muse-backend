package custom.capstone.domain.chat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String roomId;

    private String sender;

    private String receiver;

    private String message;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(
            final String roomId,
            final String sender,
            final String message,
            final ChatRoom chatRoom
    ) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.chatRoom = chatRoom;
    }
}

