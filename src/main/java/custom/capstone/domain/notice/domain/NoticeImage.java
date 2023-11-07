package custom.capstone.domain.notice.domain;

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
public class NoticeImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @Column(name = "image_url")
    private String imageUrl;

    public NoticeImage(final Notice notice, final String imageUrl) {
        this.notice = notice;
        this.imageUrl = imageUrl;

        if (!notice.getNoticeImages().contains(this)) {
            notice.getNoticeImages().add(this);
        }
    }
}
