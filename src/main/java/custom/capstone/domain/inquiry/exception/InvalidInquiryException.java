package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class InvalidInquiryException extends BaseException {
    public InvalidInquiryException() {
        super(BaseResponseStatus.INQUIRY_CANNOT_DELETE);
    }
}
