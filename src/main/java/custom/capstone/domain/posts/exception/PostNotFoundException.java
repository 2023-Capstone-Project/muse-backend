package custom.capstone.domain.posts.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class PostNotFoundException extends BaseException {
    public PostNotFoundException() {
        super(BaseResponseStatus.POST_NOT_FOUND);
    }
}
