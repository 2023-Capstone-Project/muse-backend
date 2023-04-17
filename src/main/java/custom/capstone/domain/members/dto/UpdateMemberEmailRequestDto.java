package custom.capstone.domain.members.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMemberEmailRequestDto {
    private final String email;
}
