package custom.capstone.domain.notice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;
    private LocalDateTime createAt;
    private int views;

    @Builder
    public Notice(String title, String content, int views) {
        this.title = title;
        this.content = content;
        this.views = views;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
