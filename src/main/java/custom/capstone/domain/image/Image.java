package custom.capstone.domain.image;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Image {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "img_id")
    private Long id;

    private String url;
}
