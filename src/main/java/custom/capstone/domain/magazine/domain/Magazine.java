package custom.capstone.domain.magazine.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Magazine extends BaseTimeEntity {
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

    private void setMember(final Member member) {
        this.member = member;
        member.getMagazines().add(this);
    }

    @Builder
    public Magazine(final String title, final String content, final Member member) {
        this.title = title;
        this.content = content;
        setMember(member);
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseView() {
        this.views++;
    }
}
