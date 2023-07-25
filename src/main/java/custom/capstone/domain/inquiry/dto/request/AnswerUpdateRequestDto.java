package custom.capstone.domain.inquiry.dto.request;

import javax.validation.constraints.NotBlank;

public record AnswerUpdateRequestDto(
        @NotBlank
        String content
) {
}
