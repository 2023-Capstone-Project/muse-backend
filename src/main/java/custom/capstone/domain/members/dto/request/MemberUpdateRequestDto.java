package custom.capstone.domain.members.dto.request;

public record MemberUpdateRequestDto (
        String nickname,
        String password,
        String email,
        String phoneNumber
) {
}
