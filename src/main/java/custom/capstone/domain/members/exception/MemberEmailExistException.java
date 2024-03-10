package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class MemberEmailExistException extends BusinessException {
    public MemberEmailExistException() {
        super(BaseResponseStatus.MEMBER_EXISTS_EMAIL);
    }
}
