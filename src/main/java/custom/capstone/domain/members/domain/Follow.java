package custom.capstone.domain.members.domain;

import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Follow extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "follwer_id")
    private Member from;

    @JoinColumn(name = "follwing_id")
    @ManyToOne(fetch = LAZY)
    private Member to;

    @Builder
    public Follow(final Member from, final Member to) {
        this.from = from;
        this.to = to;
    }
}
