package custom.capstone.domain.interest.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Interest {
    @Id @GeneratedValue
    @Column(name = "interest_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
