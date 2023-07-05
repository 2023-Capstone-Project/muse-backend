package custom.capstone.domain.trading.dto.request;

public record TradingSaveRequestDto(
        Long postId,
        Long buyerId,
        Long sellerId
) {
}
