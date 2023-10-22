package custom.capstone.domain.review.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class ReviewInvalidException extends BaseException {
    public ReviewInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
