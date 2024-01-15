package custom.capstone.domain.interest.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class InterestInvalidException extends BusinessException {
    public InterestInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
