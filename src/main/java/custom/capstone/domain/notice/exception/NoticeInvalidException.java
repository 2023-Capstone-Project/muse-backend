package custom.capstone.domain.notice.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class NoticeInvalidException extends BaseException {
    public NoticeInvalidException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
