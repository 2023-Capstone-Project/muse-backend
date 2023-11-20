package custom.capstone.domain.chat.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String roomId;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = REMOVE)
    private List<ChatMessage> messageList = new ArrayList<>();


    @Builder
    public ChatRoom(
            final Post post,
            final Member sender,
            final Member receiver
    ) {
        this.post = post;
        this.roomId = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
    }

    public boolean hasMember(Long memberId) {
        return sender.getId().equals(memberId) || receiver.getId().equals(memberId);
    }
}
