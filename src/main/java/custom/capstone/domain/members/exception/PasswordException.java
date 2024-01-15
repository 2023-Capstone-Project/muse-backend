package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class PasswordException extends BusinessException {
    public PasswordException() {
        super(BaseResponseStatus.WRONG_PASSWORD);
    }
}
