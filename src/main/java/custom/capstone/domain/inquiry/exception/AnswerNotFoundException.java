package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class AnswerNotFoundException extends BusinessException {
    public AnswerNotFoundException() {
        super(BaseResponseStatus.ANSWER_NOT_FOUND);
    }
}
