package custom.capstone.domain.notice.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class NoticeInvalidException extends BusinessException {
    public NoticeInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
