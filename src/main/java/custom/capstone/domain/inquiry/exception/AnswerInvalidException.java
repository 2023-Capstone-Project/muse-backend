package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class AnswerInvalidException extends BusinessException {
    public AnswerInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
