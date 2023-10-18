package custom.capstone.domain.inquiry.dto.request;

import javax.validation.constraints.NotBlank;

public record AnswerSaveRequestDto (
        @NotBlank
        String content
) {
}
