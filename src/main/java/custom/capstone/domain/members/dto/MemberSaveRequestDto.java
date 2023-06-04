package custom.capstone.domain.members.dto;

public record MemberSaveRequestDto (
        String nickname,
        String password,
        String email,
        String phoneNum
) {
}
