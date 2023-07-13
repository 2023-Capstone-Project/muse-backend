package custom.capstone.domain.interest.dto.request;

public record InterestSaveRequestDto (
        Long memberId,
        Long postId
) {
}
