package custom.capstone.domain.trading.dto.response;

import custom.capstone.domain.trading.domain.TradingStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public record TradingResponseDto (
        String seller,

        @Enumerated(EnumType.STRING)
        TradingStatus status
) {
}
