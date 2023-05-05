package custom.capstone.domain.members.domain;

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
public class Follow {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follwer_id")
    private Member from;

    @JoinColumn(name = "follwing_id")
    @ManyToOne(fetch = LAZY)
    private Member to;

    private LocalDateTime createAt = LocalDateTime.now();

    @Builder
    public Follow(Member from, Member to) {
        this.from = from;
        this.to = to;
    }
}
