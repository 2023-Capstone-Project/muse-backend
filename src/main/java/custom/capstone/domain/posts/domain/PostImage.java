package custom.capstone.domain.posts.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PostImage {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "image_url")
    private String imageUrl;

    private void setPost(final Post post) {
        this.post = post;

        if (!post.getPostImages().contains(this)) {
            post.getPostImages().add(this);
        }
    }

    @Builder
    public PostImage(final Post post,
                     final String imageUrl) {
        setPost(post);
        this.imageUrl = imageUrl;
    }
}

