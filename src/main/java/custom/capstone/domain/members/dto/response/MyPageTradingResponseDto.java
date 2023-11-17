package custom.capstone.domain.members.dto.response;

import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.trading.dto.response.TradingListResponseDto;

import java.util.List;

public record MyPageTradingResponseDto(
        MemberProfileDto memberProfileDto,
        List<TradingListResponseDto> tradingListResponseDto
) {
}
