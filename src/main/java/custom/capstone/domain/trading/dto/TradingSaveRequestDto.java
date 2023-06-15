package custom.capstone.domain.trading.dto;

public record TradingSaveRequestDto(
        Long postId,
        Long buyerId,
        Long sellerId
) {
}
