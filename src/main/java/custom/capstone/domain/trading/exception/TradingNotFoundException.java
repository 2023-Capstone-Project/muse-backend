package custom.capstone.domain.trading.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class TradingNotFoundException extends BusinessException {
    public TradingNotFoundException() {
        super(BaseResponseStatus.TRADING_NOT_FOUND);
    }
}
