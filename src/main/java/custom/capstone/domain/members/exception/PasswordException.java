package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class PasswordException extends BaseException {
    public PasswordException() {
        super(BaseResponseStatus.WRONG_PASSWORD);
    }
}
