package custom.capstone.domain.interest.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class InterestDuplicateException extends BaseException {
    public InterestDuplicateException() {
        super(BaseResponseStatus.INTEREST_EXISTS_STATUS);
    }
}
