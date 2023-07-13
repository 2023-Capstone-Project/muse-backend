package custom.capstone.domain.review.dto.response;

import custom.capstone.domain.review.domain.Review;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewListResponseDto {
    private String writer;
    private String seller;
    private String content;
    private LocalDateTime createAt;

    public ReviewListResponseDto(final Review review) {
        this.writer = review.getMember().getNickname();
        this.seller = review.getTrading().getPost().getMember().getNickname();
        this.content = review.getContent();
        this.createAt = review.getCreatedAt();
    }
}
