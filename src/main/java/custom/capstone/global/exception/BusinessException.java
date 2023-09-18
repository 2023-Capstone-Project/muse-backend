package custom.capstone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BusinessException extends RuntimeException {
    private final BaseResponseStatus status;
}
