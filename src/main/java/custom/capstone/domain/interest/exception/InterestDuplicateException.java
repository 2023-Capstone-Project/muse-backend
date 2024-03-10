package custom.capstone.domain.interest.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class InterestDuplicateException extends BusinessException {
    public InterestDuplicateException() {
        super(BaseResponseStatus.INTEREST_EXISTS_STATUS);
    }
}
