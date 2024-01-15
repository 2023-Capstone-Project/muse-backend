package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class MemberNicknameExistException extends BusinessException {
    public MemberNicknameExistException() {
        super(BaseResponseStatus.MEMBER_EXISTS_NICKNAME);
    }
}
