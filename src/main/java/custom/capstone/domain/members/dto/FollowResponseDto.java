package custom.capstone.domain.members.dto;

public record FollowResponseDto (
    Long id,
    Long fromId,
    Long toId
) {
}
