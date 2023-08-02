package custom.capstone.domain.interest.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class InterestNotFoundException extends BaseException {
    public InterestNotFoundException() {
        super(BaseResponseStatus.INTEREST_NOT_FOUND);
    }
}
