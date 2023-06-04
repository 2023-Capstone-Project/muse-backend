package custom.capstone.domain.review.dto;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.trading.domain.Trading;

public record ReviewSaveRequestDto(
        Trading product,
        Member member,
        String content
) {
}
