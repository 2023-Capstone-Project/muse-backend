package custom.capstone.global.exception;

public class ImageNotFoundException extends BaseException {
    public ImageNotFoundException () {
        super(BaseResponseStatus.IMAGE_NOT_FOUND);
    }
}
