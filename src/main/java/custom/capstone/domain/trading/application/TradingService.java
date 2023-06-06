package custom.capstone.domain.trading.application;

import custom.capstone.domain.trading.dao.TradingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradingService {
    private final TradingRepository tradingRepository;
}
