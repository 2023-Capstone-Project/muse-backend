package custom.capstone.domain.interest.dto;

import custom.capstone.domain.interest.domain.Interest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestListResponseDto {
    private String title;
    private String seller;
    private int price;
    private LocalDateTime createAt;

    public InterestListResponseDto(Interest interest) {
        this.title = interest.getPost().getTitle();
        this.seller = interest.getPost().getMember().getNickname();
        this.price = interest.getPost().getPrice();
        this.createAt = interest.getPost().getCreateAt();
    }
}
