package custom.capstone.domain.trading.dao;

import custom.capstone.domain.trading.domain.TradingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingProductRepository extends JpaRepository<TradingProduct, Long> {
}