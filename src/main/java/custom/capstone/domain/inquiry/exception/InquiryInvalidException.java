package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class InquiryInvalidException extends BusinessException {
    public InquiryInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
