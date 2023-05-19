package custom.capstone.domain.trading.application;

import custom.capstone.domain.trading.dao.TradingProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingProductService {
    private final TradingProductRepository tradingProductRepository;
}
