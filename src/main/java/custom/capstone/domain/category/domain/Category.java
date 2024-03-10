package custom.capstone.domain.category.domain;

import custom.capstone.domain.posts.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Category {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;

    @OneToMany(mappedBy = "category")
    private final Set<Post> posts = new HashSet<>();

    @Builder
    public Category(final String title) {
        this.title = title;
    }

    public void updateTitle(final String title) {
        this.title = title;
    }
}
