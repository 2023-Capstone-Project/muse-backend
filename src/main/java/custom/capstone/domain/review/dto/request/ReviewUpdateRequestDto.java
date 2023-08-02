package custom.capstone.domain.review.dto.request;

import javax.validation.constraints.NotBlank;

public record ReviewUpdateRequestDto(
        @NotBlank
        String content
) {
}
