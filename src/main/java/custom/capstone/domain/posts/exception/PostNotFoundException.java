package custom.capstone.domain.posts.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class PostNotFoundException extends BusinessException {
    public PostNotFoundException() {
        super(BaseResponseStatus.POST_NOT_FOUND);
    }
}
