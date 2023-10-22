package custom.capstone.domain.notice.dto.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record NoticeSaveRequestDto (
        @NotBlank
        @Length(min = 1, max = 50, message = "제목은 50자 이하로 입력해주세요.")
        String title,

        @NotBlank(message = "내용을 입력해 주세요.")
        String content
) {
}
