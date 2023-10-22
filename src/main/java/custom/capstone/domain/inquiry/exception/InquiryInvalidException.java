package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class InquiryInvalidException extends BaseException {
    public InquiryInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
