package custom.capstone.domain.inquiry.dto.request;

import javax.validation.constraints.NotBlank;

public record InquirySaveRequestDto (
        Long memberId,
        @NotBlank
        String title,
        @NotBlank
        String content
) {
}
