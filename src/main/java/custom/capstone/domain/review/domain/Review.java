package custom.capstone.domain.review.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.trading.domain.Trading;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trading_id")
    private Trading trading;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500)
    private String content;

    private LocalDateTime creatAt;

    @Builder
    public Review(Trading trading, Member member, String content) {
        this.trading = trading;
        this.member = member;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
