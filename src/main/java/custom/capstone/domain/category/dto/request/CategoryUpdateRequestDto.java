package custom.capstone.domain.category.dto.request;

import javax.validation.constraints.NotBlank;

public record CategoryUpdateRequestDto (
        @NotBlank
        String title
) {
}
