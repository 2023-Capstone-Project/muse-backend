package custom.capstone.domain.chat.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ChatRoom implements Serializable {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String sender;

    private String receiver;

    @OneToMany(mappedBy = "chatRoom", cascade = REMOVE)
    private List<Message> messageList = new ArrayList<>();

    public ChatRoom(
            final String sender,
            final String roomId,
            final String receiver,
            final Member member,
            final Post post
    ) {
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.member = member;
        this.post = post;
    }
}
