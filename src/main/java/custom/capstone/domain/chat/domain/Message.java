package custom.capstone.domain.chat.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String roomId;

    private String sender;

    private String receiver;

    private String message;

    @ManyToOne
    @JoinColumn(
            name = "roomId",
            referencedColumnName = "roomId",
            insertable = false,
            updatable = false
    )
    private ChatRoom chatRoom;

    public Message(
            final String sender,
            final String roomId,
            final String message
    ) {
        super();
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
    }
}

