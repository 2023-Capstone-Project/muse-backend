package custom.capstone.domain.category.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String title;

    @Builder
    public Category(String title) {
        this.title = title;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
