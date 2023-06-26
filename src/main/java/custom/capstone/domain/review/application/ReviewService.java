package custom.capstone.domain.review.application;

import custom.capstone.domain.review.dao.ReviewRepository;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    /**
     * 후기 생성
     */
    @Transactional
    public Long saveReview(final ReviewSaveRequestDto requestDto) {
        Review review = Review.builder()
                .trading(requestDto.product())
                .member(requestDto.member())
                .content(requestDto.content())
                .build();

        return reviewRepository.save(review).getId();
    }

    /**
     * 후기 수정
     */
    @Transactional
    public Long updateReview(final Long reviewId, final ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new NotFoundException("해당 거래 상품은 존재하지 않습니다."));

        review.update(requestDto.content());

        return reviewId;
    }

    /**
     * 후기 삭제
     */
    @Transactional
    public void deleteReview(final Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()->new NotFoundException("거래 상품을 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }

    /**
     * 후기 조회
     */
    public Review findById(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException("후기를 조회할 수 없습니다."));
    }
}
