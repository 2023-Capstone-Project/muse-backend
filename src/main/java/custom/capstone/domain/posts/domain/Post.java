package custom.capstone.domain.posts.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.model.domain.Category;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private char deleteYn;  //삭제여부 ... 일단 넣음
    private PostStatus status;
    private int likeCnt;
    private int views;

    @Builder
    public Post(String title, String content, Member member, int views, char deleteYn) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.views = views;
        this.deleteYn = deleteYn;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updateAt = LocalDateTime.now();
    }
}
