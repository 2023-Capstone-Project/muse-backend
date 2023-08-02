package custom.capstone.domain.inquiry.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class AnswerNotFoundException extends BaseException {
    public AnswerNotFoundException() {
        super(BaseResponseStatus.ANSWER_NOT_FOUND);
    }
}
