package custom.capstone.domain.review.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class ReviewNotFoundException extends BaseException {
    public ReviewNotFoundException() {
        super(BaseResponseStatus.REVIEW_NOT_FOUND);
    }
}
