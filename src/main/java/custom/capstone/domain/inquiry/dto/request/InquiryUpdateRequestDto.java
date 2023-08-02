package custom.capstone.domain.inquiry.dto.request;

import javax.validation.constraints.NotBlank;

public record InquiryUpdateRequestDto(
        @NotBlank
        String title,

        @NotBlank
        String content
) {
}
