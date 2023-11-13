package custom.capstone.domain.review.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trading_id")
    private Trading trading;

    @Column(length = 500)
    private String content;

    private void setTrading(final Trading trading) {
        this.trading = trading;
        trading.getReviews().add(this);
    }

    @Builder
    public Review(
            final Trading trading,
            final Member member,
            final String content
    ) {
        this.setTrading(trading);
        this.member = member;
        this.content = content;
    }

    public void update(final String content) {
        this.content = content;
    }
}
