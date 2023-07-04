package custom.capstone.domain.members.dto.response;

public record MemberLoginResponseDto(
        Long memberId,
        String accessToken
) {
}
