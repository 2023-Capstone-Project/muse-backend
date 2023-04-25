package custom.capstone.domain.members.dto;

import javax.validation.constraints.NotBlank;

public record UpdateMemberDto(
        @NotBlank String name,
        @NotBlank String password,
        @NotBlank String email,
        @NotBlank String phoneNum
) {
}
