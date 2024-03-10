package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class LoginFailException extends BusinessException {

    public LoginFailException() {
        super(BaseResponseStatus.MEMBER_FAILED_LOGIN);
    }
}
