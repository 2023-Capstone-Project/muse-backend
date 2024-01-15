package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class JoinPasswordException extends BusinessException {
    public JoinPasswordException() {
        super(BaseResponseStatus.WRONG_JOIN_PASSWORD);
    }
}
