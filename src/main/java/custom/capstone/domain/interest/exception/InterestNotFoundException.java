package custom.capstone.domain.interest.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class InterestNotFoundException extends BusinessException {
    public InterestNotFoundException() {
        super(BaseResponseStatus.INTEREST_NOT_FOUND);
    }
}
