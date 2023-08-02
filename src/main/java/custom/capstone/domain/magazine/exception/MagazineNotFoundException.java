package custom.capstone.domain.magazine.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class MagazineNotFoundException extends BaseException {
    public MagazineNotFoundException() {
        super(BaseResponseStatus.MEMBER_NOT_FOUND);
    }
}
