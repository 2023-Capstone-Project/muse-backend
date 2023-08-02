package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class JoinPasswordException extends BaseException {
    public JoinPasswordException() {
        super(BaseResponseStatus.WRONG_JOIN_PASSWORD);
    }
}
