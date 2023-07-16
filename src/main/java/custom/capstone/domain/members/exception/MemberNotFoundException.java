package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class MemberNotFoundException extends BaseException {
    public MemberNotFoundException() {
        super(BaseResponseStatus.MEMBER_NOT_FOUND);
    }
}
