package custom.capstone.domain.notice.domain;

import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notice extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    private int views;

    @Builder
    public Notice(final String title, final String content, final int views) {
        this.title = title;
        this.content = content;
        this.views = views;
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}
