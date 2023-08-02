package custom.capstone.domain.review.dto.request;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.trading.domain.Trading;

import javax.validation.constraints.NotBlank;

public record ReviewSaveRequestDto(
        Trading product,
        Member member,

        @NotBlank
        String content
) {
}
