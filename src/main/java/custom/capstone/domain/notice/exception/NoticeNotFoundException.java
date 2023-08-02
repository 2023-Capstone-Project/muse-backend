package custom.capstone.domain.notice.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class NoticeNotFoundException extends BaseException {
    public NoticeNotFoundException() {
        super(BaseResponseStatus.NOTICE_NOT_FOUND);
    }
}
