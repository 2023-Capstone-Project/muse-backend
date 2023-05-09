package custom.capstone.domain.members.dto;

public record FollowSaveRequestDto(
    Long fromId,
    Long toId
) {
}
