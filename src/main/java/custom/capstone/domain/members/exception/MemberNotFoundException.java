package custom.capstone.domain.members.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super(BaseResponseStatus.MEMBER_NOT_FOUND);
    }
}
