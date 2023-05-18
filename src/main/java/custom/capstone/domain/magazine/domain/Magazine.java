package custom.capstone.domain.magazine.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Magazine {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mgz_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private int views;

    @Builder
    public Magazine(String title, String content, int views) {
        this.title = title;
        this.content = content;
        this.views = views;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
