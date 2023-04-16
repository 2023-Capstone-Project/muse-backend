package custom.capstone.domain.trading.domain;

import custom.capstone.project.domain.members.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class TradingReviews {
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
}
