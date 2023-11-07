package custom.capstone.domain.posts.domain;

import custom.capstone.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostImage  extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url")
    private String imageUrl;

    public PostImage(final Post post, final String imageUrl) {
        this.post = post;
        this.imageUrl = imageUrl;

        if (!post.getPostImages().contains(this)) {
            post.getPostImages().add(this);
        }
    }
}

