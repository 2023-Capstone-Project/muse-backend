package custom.capstone.domain.interest.dto.response;

import custom.capstone.domain.interest.domain.Interest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class InterestListResponseDto {
    private String title;
    private String seller;
    private int price;
    private LocalDateTime createAt;

    public InterestListResponseDto(final Interest interest) {
        this.title = interest.getPost().getTitle();
        this.seller = interest.getPost().getMember().getNickname();
        this.price = interest.getPost().getPrice();
        this.createAt = interest.getPost().getCreatedAt();
    }
}
