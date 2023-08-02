package custom.capstone.domain.inquiry.domain;

import custom.capstone.global.common.BaseTimeEntity;
import lombok.Builder;
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
public class Answer extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String admin;

    private String content;

    @Builder
    public Answer(final String admin, final String content) {
        this.admin = admin;
        this.content = content;
    }

    public void update(final String content) {
        this.content = content;
    }
}
