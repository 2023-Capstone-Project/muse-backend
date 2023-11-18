package custom.capstone.domain.chat.domain;

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

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String roomId;

    private String sender;

    private String receiver;

    @OneToMany(mappedBy = "chatRoom", cascade = REMOVE)
    private List<ChatMessage> messageList = new ArrayList<>();

    @Builder
    public ChatRoom(
            final Post post,
            final String sender,
            final String receiver
    ) {
        this.post = post;
        this.roomId = UUID.randomUUID().toString();
        this.sender = sender;
        this.receiver = receiver;
    }
}
