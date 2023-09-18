package custom.capstone.global.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import custom.capstone.global.exception.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})    // json 요청 순서 정의
public class BaseResponse<T> {
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)  // 결과값이 공백일 경우 json 에 포함하지 않도록 한다
    private final T result;

    @JsonIgnore
    private final HttpStatus httpStatus;

    public static <T> BaseResponse<T> of (
            final BaseResponseStatus status,
            final T result
    ) {
        return new BaseResponse<>(
                status.getCode(),
                status.getMessage(),
                result,
                status.getHttpStatus()
        );
    }
}
