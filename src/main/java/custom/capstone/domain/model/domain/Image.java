package custom.capstone.domain.model.domain;

import custom.capstone.project.domain.posts.domain.Post;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Image {
    @Id @GeneratedValue
    @Column(name = "img_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Long url;
}
