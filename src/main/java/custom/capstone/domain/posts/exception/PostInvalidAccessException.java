package custom.capstone.domain.posts.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class PostInvalidAccessException extends BusinessException {
    public PostInvalidAccessException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
