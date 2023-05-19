package custom.capstone.domain.trading.domain;

import custom.capstone.domain.posts.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class TradingProduct {
    @Id @GeneratedValue
    @Column(name = "tp_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(length = 50)
    private String title;

    private LocalDateTime dealAt;
}
