package custom.capstone.global.exception;

import custom.capstone.global.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handlerMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        log.error("[Error] {}", ex.getMessage());
        return buildResponse(BaseResponseStatus.DTO_INVALID);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<?>> handlerBusinessException(final BusinessException ex) {
        log.error("[Error] HttpStatus: {}, Message: {}", ex.getStatus(), ex.getMessage());
        return buildResponse(ex.getStatus());
    }

    private ResponseEntity<BaseResponse<?>> buildResponse(final BaseResponseStatus status) {
        final BaseResponse<?> baseResponse = BaseResponse.of(status, null);
        return new ResponseEntity<>(baseResponse, status.getHttpStatus());
    }

}
