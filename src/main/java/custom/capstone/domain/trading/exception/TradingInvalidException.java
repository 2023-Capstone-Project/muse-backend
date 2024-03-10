package custom.capstone.domain.trading.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class TradingInvalidException extends BusinessException {
    public TradingInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
