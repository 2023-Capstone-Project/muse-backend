package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class MemberEmailExistException extends BaseException {
    public MemberEmailExistException() {
        super(BaseResponseStatus.MEMBER_EXISTS_EMAIL);
    }
}
