package custom.capstone.global.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import custom.capstone.global.exception.BaseResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static custom.capstone.global.exception.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"success", "code", "message", "result"})    // json 요청 순서 정의
public class BaseResponse<T> {
    private Boolean isSuccess;
    private int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)  // 결과값이 공백일 경우 json 에 포함하지 않도록 한다
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(final T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(final BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
