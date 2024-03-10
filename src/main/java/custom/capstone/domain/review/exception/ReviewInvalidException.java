package custom.capstone.domain.review.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class ReviewInvalidException extends BusinessException {
    public ReviewInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
