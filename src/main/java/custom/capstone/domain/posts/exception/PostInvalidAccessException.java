package custom.capstone.domain.posts.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class PostInvalidAccessException extends BaseException {
    public PostInvalidAccessException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
