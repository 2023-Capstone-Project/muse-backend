package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class InquiryNotFoundException extends BaseException {
    public InquiryNotFoundException() {
        super(BaseResponseStatus.INQUIRY_NOT_FOUND);
    }
}
