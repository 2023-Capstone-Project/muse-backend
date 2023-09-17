package custom.capstone.domain.trading.dto.response;

import custom.capstone.domain.trading.domain.TradingStatus;

public record TradingResponseDto (
        String seller,
        TradingStatus status
) {
}
