package custom.capstone.domain.notice.domain;

import custom.capstone.domain.admin.Admin;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_code")
    private Admin admin;

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
