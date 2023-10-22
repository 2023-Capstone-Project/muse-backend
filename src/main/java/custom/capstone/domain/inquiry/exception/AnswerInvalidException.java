package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class AnswerInvalidException extends BaseException {
    public AnswerInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
