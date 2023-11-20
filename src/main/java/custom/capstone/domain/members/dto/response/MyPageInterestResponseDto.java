package custom.capstone.domain.members.dto.response;

import custom.capstone.domain.interest.dto.response.InterestListResponseDto;
import custom.capstone.domain.members.dto.MemberProfileDto;

import java.util.List;

public record MyPageInterestResponseDto (
        MemberProfileDto memberProfileDto,
        List<InterestListResponseDto> interestListResponseDto
) {
}
