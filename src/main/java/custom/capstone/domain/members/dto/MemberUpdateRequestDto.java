package custom.capstone.domain.members.dto;

public record MemberUpdateRequestDto (
        String nickname,
        String password,
        String email,
        String phoneNum
) {
}
