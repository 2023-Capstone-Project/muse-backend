package custom.capstone.domain.inquiry.domain;

import custom.capstone.domain.members.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inquiry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    @Builder
    public Inquiry(Member member, String title, String content, LocalDateTime createAt) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.updateAt = LocalDateTime.now();
    }
}
