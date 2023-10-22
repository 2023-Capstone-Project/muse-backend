package custom.capstone.domain.interest.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class InterestInvalidException extends BaseException {
    public InterestInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
