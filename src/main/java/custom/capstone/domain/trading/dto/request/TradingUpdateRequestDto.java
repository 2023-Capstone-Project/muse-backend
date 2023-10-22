package custom.capstone.domain.trading.dto.request;

import custom.capstone.domain.trading.domain.TradingStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public record TradingUpdateRequestDto(
        @Enumerated(EnumType.STRING)
        TradingStatus status
) {
}
