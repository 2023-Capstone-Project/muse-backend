package custom.capstone.domain.trading.dto;

import custom.capstone.domain.members.domain.Member;
import java.time.LocalDateTime;

public record TradingReviewListResponseDto (
        Long productId,
        Member member,
        String content,
        LocalDateTime creatAt
) {
}
