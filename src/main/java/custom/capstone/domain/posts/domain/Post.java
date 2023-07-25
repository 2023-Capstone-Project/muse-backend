package custom.capstone.domain.posts.domain;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    private int price;

    @Setter @Enumerated(STRING)
    @Column(nullable = false)
    private PostStatus status;

    @Enumerated(STRING)
    @Column(nullable = false)
    private PostType type;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    @OneToMany(mappedBy = "post")
    private final Set<Interest> interests = new HashSet<>();

    @Builder
    public Post(
            final String title,
            final String content,
            final int price,
            final Member member,
            final PostType type) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.member = member;
        this.status = PostStatus.SALE;
        this.type = type;
    }

    public void update(
            final String title,
            final String content,
            final int price,
            final PostType type) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.type = type;
    }

    public void increaseView() {
        this.views++;
    }
}
