package custom.capstone.domain.category.dto.request;

import javax.validation.constraints.NotBlank;

public record CategorySaveRequestDto (
        @NotBlank
        String title
) {
}
