package custom.capstone.domain.trading.dto;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.trading.domain.TradingStatus;

public record TradingUpdateRequestDto(
        Post post,
        Member buyer,
        Member seller,
        TradingStatus status
) {
}
