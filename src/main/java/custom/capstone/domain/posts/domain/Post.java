package custom.capstone.domain.posts.domain;

import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.members.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING) @Setter
    @Column(nullable = false)
    private PostStatus status;

    private int likeCnt;
    private int views;

    @Builder
    public Post(final String title, final String content, final int price, final Member member) {
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
        this.updateAt = LocalDateTime.now();
    }
}
