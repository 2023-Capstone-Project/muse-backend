package custom.capstone.global.exception;

public class InvalidAccessException extends BaseException{
    public InvalidAccessException() {
        super(BaseResponseStatus.INVALID_ACCESS);
    }
}
