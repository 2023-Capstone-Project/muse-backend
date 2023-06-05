package custom.capstone.domain.interest.dto;

import custom.capstone.domain.interest.domain.Interest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestResponseDto {
    private String seller;
    private String title;
    private int price;

    public InterestResponseDto(Interest interest) {
        this.seller = interest.getMember().getNickname();
        this.title = interest.getPost().getTitle();
        this.price = interest.getPost().getPrice();
    }
}
