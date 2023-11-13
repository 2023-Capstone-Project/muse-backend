package custom.capstone.domain.notice.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Notice extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    @OneToMany(
            mappedBy = "notice",
            fetch = LAZY,
            cascade = {PERSIST, REMOVE},
            orphanRemoval = true
    )
    private final List<NoticeImage> noticeImages = new ArrayList<>();

    private void setMember(final Member member) {
        this.member = member;
        member.getNotices().add(this);
    }

    @Builder
    public Notice(
            final String title,
            final String content,
            final Member member
    ) {
        this.title = title;
        this.content = content;
        this.setMember(member);
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseView() {
        this.views++;
    }
}
