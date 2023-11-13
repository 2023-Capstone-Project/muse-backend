package custom.capstone.domain.posts.domain;

import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

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

    @Column(name = "interest_cnt", columnDefinition = "integer default 0", nullable = false)
    private int interestCount;

    @OneToMany(mappedBy = "post")
    private final Set<Interest> interests = new HashSet<>();

    @OneToMany(
            mappedBy = "post",
            fetch = LAZY,
            cascade = {PERSIST, REMOVE},
            orphanRemoval = true
    )
    private final List<PostImage> postImages = new ArrayList<>();

    public void setCategory(final Category category) {
        this.category = category;
        category.getPosts().add(this);
    }

    @Builder
    public Post(
            final String title,
            final String content,
            final int price,
            final Member member,
            final Category category,
            final PostType type
    ) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.member = member;
        this.status = PostStatus.SALE;
        setCategory(category);
        this.type = type;
    }

    public void update(
            final String title,
            final String content,
            final int price,
            final Category category,
            final PostType type,
            final PostStatus status
    ) {
        this.title = title;
        this.content = content;
        this.price = price;
        setCategory(category);
        this.type = type;
        this.status = status;
    }

    public void increaseView() {
        this.views++;
    }

    public void increaseInterestCount() {
        this.interestCount++;
    }

    public void decreaseInterestCount() {
        if (this.interestCount > 0) {
            this.interestCount--;
        }
    }
}
