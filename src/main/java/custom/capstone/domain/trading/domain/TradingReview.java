package custom.capstone.domain.trading.domain;

import custom.capstone.domain.members.domain.Member;
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
public class TradingReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tr_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tp_id")
    private TradingProduct tradingProduct;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 500)
    private String content;

    private LocalDateTime creatAt;

    @Builder
    public TradingReview(TradingProduct tradingProduct, Member member, String content) {
        this.tradingProduct = tradingProduct;
        this.member = member;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
