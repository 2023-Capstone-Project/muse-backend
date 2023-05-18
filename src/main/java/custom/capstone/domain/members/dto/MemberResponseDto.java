package custom.capstone.domain.members.dto;

import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.domain.MemberStatus;

public record MemberResponseDto(
        Long id,
        String nickname,
        String email,
        String phoneNUm,
        MemberRole occupation,
        MemberStatus status
) {
}
