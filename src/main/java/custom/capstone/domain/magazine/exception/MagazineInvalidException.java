package custom.capstone.domain.magazine.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class MagazineInvalidException extends BaseException {
    public MagazineInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
