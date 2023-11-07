package custom.capstone.domain.interest.dto.response;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.posts.domain.PostImage;
import custom.capstone.global.exception.ImageNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class InterestListResponseDto {
    private Long interestId;
    private String title;
    private String seller;
    private int price;
    private LocalDateTime createAt;
    private String thumbnailUrl;

    public InterestListResponseDto(final Interest interest) {
        this.interestId = interest.getId();
        this.title = interest.getPost().getTitle();
        this.seller = interest.getPost().getMember().getNickname();
        this.price = interest.getPost().getPrice();
        this.createAt = interest.getPost().getCreatedAt();
        this.thumbnailUrl = interest.getPost().getPostImages()
                .stream().findFirst()
                .map(PostImage::getImageUrl)
                .orElseThrow(ImageNotFoundException::new);
    }
}
