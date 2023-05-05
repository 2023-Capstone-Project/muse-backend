package custom.capstone.domain.members.dto;

import custom.capstone.domain.members.domain.MemberOccupation;

public record MemberSaveRequestDto (
        String nickname,
        String password,
        String email,
        String phoneNum,
        MemberOccupation occupation
) {
}
