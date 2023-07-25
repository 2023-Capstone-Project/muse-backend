package custom.capstone.domain.inquiry.dto.request;

import javax.validation.constraints.NotBlank;

public record AnswerSaveRequestDto (
        Long memberId,

        @NotBlank
        String content
) {
}
