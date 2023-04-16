package custom.capstone.domain.posts.domain;

import custom.capstone.project.domain.members.domain.Member;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class InterestList {
    @Id @GeneratedValue
    @Column(name = "intLst_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
