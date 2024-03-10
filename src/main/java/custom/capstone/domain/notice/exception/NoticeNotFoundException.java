package custom.capstone.domain.notice.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class NoticeNotFoundException extends BusinessException {
    public NoticeNotFoundException() {
        super(BaseResponseStatus.NOTICE_NOT_FOUND);
    }
}
