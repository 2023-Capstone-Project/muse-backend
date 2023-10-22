package custom.capstone.domain.review.dto.request;

import javax.validation.constraints.NotBlank;

public record ReviewSaveRequestDto(
        Long tradingId,

        @NotBlank
        String content
) {
}
