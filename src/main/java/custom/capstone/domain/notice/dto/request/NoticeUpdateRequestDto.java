package custom.capstone.domain.notice.dto.request;

import javax.validation.constraints.NotBlank;

public record NoticeUpdateRequestDto(
        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
