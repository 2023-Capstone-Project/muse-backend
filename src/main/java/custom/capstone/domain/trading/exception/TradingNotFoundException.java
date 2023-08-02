package custom.capstone.domain.trading.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class TradingNotFoundException extends BaseException {
    public TradingNotFoundException() {
        super(BaseResponseStatus.TRADING_NOT_FOUND);
    }
}
