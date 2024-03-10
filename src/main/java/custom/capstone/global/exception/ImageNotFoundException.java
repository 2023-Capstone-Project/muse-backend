package custom.capstone.global.exception;

public class ImageNotFoundException extends BusinessException {
    public ImageNotFoundException () {
        super(BaseResponseStatus.IMAGE_NOT_FOUND);
    }
}
