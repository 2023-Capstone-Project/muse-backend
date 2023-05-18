package custom.capstone.domain.members.dto;

import custom.capstone.domain.members.domain.MemberRole;

public record MemberSaveRequestDto (
        String nickname,
        String password,
        String email,
        String phoneNum,
        MemberRole occupation
) {
}
