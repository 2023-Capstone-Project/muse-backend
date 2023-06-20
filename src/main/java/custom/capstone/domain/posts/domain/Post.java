package custom.capstone.domain.posts.domain;

import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

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

    private int likeCnt;

    private int views;

    @Builder
    public Post(
            final String title,
            final String content,
            final int price,
            final Member member) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.member = member;
        this.status = PostStatus.SALE;
    }

    public void update(final String title, final String content, final int price) {
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
