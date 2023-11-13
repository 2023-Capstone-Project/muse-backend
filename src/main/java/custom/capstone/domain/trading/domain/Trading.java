package custom.capstone.domain.trading.domain;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostStatus;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Trading extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;           // 구매자

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;          // 판매자 = 작성자

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TradingStatus status;

    @OneToMany(mappedBy = "trading")
    private final Set<Review> reviews = new HashSet<>();

    @Builder
    public Trading(
            final Post post,
            final Member buyer,
            final Member seller
    ) {
        this.post = post;
        this.buyer = buyer;
        this.seller = seller;
        this.status = TradingStatus.PENDING;
    }

    public void update(final TradingStatus status) {
        this.status = status;
        setPostStatus(status);
    }

    private void setPostStatus(final TradingStatus status) {
        switch (status) {
            case PENDING, CANCEL -> post.setStatus(PostStatus.SALE);
            case APPROVE -> post.setStatus(PostStatus.SOLD);
            case RESERVE -> post.setStatus(PostStatus.RESERVED);
            default -> throw new IllegalArgumentException("Invalid TradingStatus");
        }
    }
}
