package custom.capstone.domain.trading.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.trading.domain.Trading;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradingListResponseDto {
    private String title;
    private MemberProfileDto seller;
    private String status;
    private int price;
    private LocalDateTime dealAt;

    public TradingListResponseDto(Trading trading) {
        this.title = trading.getPost().getTitle();
        this.seller = new MemberProfileDto(
                trading.getSeller().getId(),
                trading.getSeller().getNickname(),
                trading.getSeller().getProfileImage()
        );
        this.status = trading.getPost().getStatus().toString();
        this.price = trading.getPost().getPrice();
        this.dealAt = trading.getCreatedAt();
    }
}
