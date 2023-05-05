package custom.capstone.domain.members.dto;

import custom.capstone.domain.members.domain.MemberOccupation;
import custom.capstone.domain.members.domain.MemberStatus;

public record MemberResponseDto(
        Long id,
        String nickname,
        String email,
        String phoneNUm,
        MemberOccupation occupation,
        MemberStatus status
) {
}
