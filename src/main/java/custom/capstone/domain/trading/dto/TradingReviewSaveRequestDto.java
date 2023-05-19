package custom.capstone.domain.trading.dto;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.trading.domain.TradingProduct;

public record TradingReviewSaveRequestDto (
        TradingProduct product,
        Member member,
        String content
) {
}
