package custom.capstone.domain.trading.dto.request;

import custom.capstone.domain.trading.domain.TradingStatus;

public record TradingUpdateRequestDto(
        Long postId,
        TradingStatus status
) {
}
