package custom.capstone.domain.trading.application;

import custom.capstone.domain.trading.dao.TradingReviewRepository;
import custom.capstone.domain.trading.domain.TradingReview;
import custom.capstone.domain.trading.dto.TradingReviewListResponseDto;
import custom.capstone.domain.trading.dto.TradingReviewSaveRequestDto;
import custom.capstone.domain.trading.dto.TradingReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradingReviewService {
    private final TradingReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(TradingReviewSaveRequestDto requestDto) {
        TradingReview review = TradingReview.builder()
                .tradingProduct(requestDto.product())
                .member(requestDto.member())
                .content(requestDto.content())
                .build();

        return reviewRepository.save(review).getId();
    }

    @Transactional
    public Long updateReview(Long id, TradingReviewUpdateRequestDto requestDto) {
        TradingReview review = reviewRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 거래 상품은 존재하지 않습니다."));

        review.update(requestDto.content());

        return id;
    }

    @Transactional
    public List<TradingReviewListResponseDto> findReviewByMemberId(Long id) {
        return reviewRepository.findTradingReviewByMemberId(id)
                .stream()
                .map(entity -> new TradingReviewListResponseDto(entity.productId(), entity.member(), entity.content(), entity.creatAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long id) {
        TradingReview review = reviewRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("거래 상품을 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }
}
