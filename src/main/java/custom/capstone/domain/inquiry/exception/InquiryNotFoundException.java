package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class InquiryNotFoundException extends BusinessException {
    public InquiryNotFoundException() {
        super(BaseResponseStatus.INQUIRY_NOT_FOUND);
    }
}
