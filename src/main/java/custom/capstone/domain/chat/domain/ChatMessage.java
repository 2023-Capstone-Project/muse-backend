package custom.capstone.domain.chat.domain;

import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ChatMessage extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String sender;

    private String channelId;

    private String message;

    @Builder
    public ChatMessage(final String sender,
                       final String channelId,
                       final String message) {
        this.sender = sender;
        this.channelId = channelId;
        this.message = message;
    }
}
