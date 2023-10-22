package custom.capstone.domain.interest.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Interest {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private void setMember(final Member member) {
        this.member = member;
        member.getInterests().add(this);
    }

    private void setPost(final Post post) {
        this.post = post;
        post.getInterests().add(this);
    }

    public Interest(final Member member, final Post post) {
        setMember(member);
        setPost(post);
    }
}
