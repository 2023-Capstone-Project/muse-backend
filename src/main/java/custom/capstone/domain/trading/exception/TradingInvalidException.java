package custom.capstone.domain.trading.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class TradingInvalidException extends BaseException {
    public TradingInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
