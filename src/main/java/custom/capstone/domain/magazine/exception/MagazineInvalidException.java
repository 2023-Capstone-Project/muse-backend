package custom.capstone.domain.magazine.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class MagazineInvalidException extends BusinessException {
    public MagazineInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
