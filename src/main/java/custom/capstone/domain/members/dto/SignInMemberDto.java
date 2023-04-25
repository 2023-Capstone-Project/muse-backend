package custom.capstone.domain.members.dto;

import javax.validation.constraints.NotBlank;

public record SignInMemberDto(
        @NotBlank
        Long id,
        @NotBlank
        String password
) {
}
