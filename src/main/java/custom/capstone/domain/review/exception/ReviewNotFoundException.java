package custom.capstone.domain.review.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class ReviewNotFoundException extends BusinessException {
    public ReviewNotFoundException() {
        super(BaseResponseStatus.REVIEW_NOT_FOUND);
    }
}
