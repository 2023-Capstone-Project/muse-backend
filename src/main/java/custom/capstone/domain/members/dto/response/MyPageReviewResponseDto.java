package custom.capstone.domain.members.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.review.dto.response.ReviewListResponseDto;

import java.util.List;

public record MyPageReviewResponseDto (
        MemberProfileDto memberProfileDto,
        List<ReviewListResponseDto> reviewListResponseDto
) {
}
