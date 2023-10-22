package custom.capstone.domain.trading.dao;

import custom.capstone.domain.trading.domain.Trading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingRepository extends JpaRepository<Trading, Long> {
    boolean existsTradingByBuyerAndSellerAndPost(final Long buyerId, final Long sellerId, final Long postId);
}