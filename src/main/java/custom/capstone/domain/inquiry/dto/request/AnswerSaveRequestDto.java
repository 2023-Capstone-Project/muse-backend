package custom.capstone.domain.inquiry.dto.request;

import javax.validation.constraints.NotBlank;

public record AnswerSaveRequestDto (
        Long inquiryId,

        @NotBlank
        String content
) {
}
