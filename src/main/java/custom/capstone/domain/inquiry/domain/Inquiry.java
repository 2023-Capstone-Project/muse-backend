package custom.capstone.domain.inquiry.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Inquiry extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private String title;

    private String content;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int views;

    private void setMember(final Member member) {
        this.member = member;
        member.getInquiries().add(this);
    }

    public void setAnswer(final Answer answer) {
        this.answer = answer;
    }

    @Builder
    public Inquiry(
            final Member member,
            final String title,
            final String content,
            final Answer answer
    ) {
        setMember(member);
        this.title = title;
        this.content = content;
        this.answer = answer;
        setAnswer(answer);
    }

    public void update(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseView() {
        this.views++;
    }
}
