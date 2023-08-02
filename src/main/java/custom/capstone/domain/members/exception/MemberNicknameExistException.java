package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class MemberNicknameExistException extends BaseException {
    public MemberNicknameExistException() {
        super(BaseResponseStatus.POST_MEMBER_EXISTS_NICKNAME);
    }
}
