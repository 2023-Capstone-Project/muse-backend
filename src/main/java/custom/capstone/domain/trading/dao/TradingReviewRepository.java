package custom.capstone.domain.trading.dao;

import custom.capstone.domain.trading.domain.TradingReview;
import custom.capstone.domain.trading.dto.TradingReviewListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingReviewRepository extends JpaRepository<TradingReview, Long> {
    List<TradingReviewListResponseDto> findTradingReviewByMemberId(Long memberId);
}
