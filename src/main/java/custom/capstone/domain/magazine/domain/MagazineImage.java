package custom.capstone.domain.magazine.domain;

import custom.capstone.global.common.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MagazineImage  extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    @Column(name = "image_url")
    private String imageUrl;

    public MagazineImage(final Magazine magazine, final String imageUrl) {
        this.magazine = magazine;
        this.imageUrl = imageUrl;

        if (!magazine.getMagazineImages().contains(this)){
            magazine.getMagazineImages().add(this);
        }
    }
}
