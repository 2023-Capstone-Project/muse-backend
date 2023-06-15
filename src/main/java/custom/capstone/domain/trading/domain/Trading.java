package custom.capstone.domain.trading.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberStatus;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Trading {
    @Id @GeneratedValue
    @Column(name = "trading_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradingStatus status;

    private int limitDate; // 이건 post 에 있는 게 맞을까 ?

    private LocalDateTime dealAt;

    @Builder
    public Trading(final Post post, final Member buyer, final Member seller) {
        this.post = post;
        this.buyer = buyer;
        this.seller = seller;
        this.status = TradingStatus.PENDING;
    }

    public void update(final Post post, final Member buyer, final Member seller, final TradingStatus status) {
        this.post = post;
        this.buyer = buyer;
        this.seller = seller;
        this.status = status;
    }

    public Post setPostStatus() {
        if (status.equals(TradingStatus.PENDING)) { post.setStatus(PostStatus.SALE); }
        if (status.equals(TradingStatus.APPROVE)) { post.setStatus(PostStatus.SOLD); }
        if (status.equals(TradingStatus.RESERVE)) { post.setStatus(PostStatus.RESERVED); }
        if (status.equals(TradingStatus.CANCEL)) { post.setStatus(PostStatus.SALE); }

        return post;
    }
}
