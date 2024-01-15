package custom.capstone.domain.magazine.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class MagazineNotFoundException extends BusinessException {
    public MagazineNotFoundException() {
        super(BaseResponseStatus.MEMBER_NOT_FOUND);
    }
}
