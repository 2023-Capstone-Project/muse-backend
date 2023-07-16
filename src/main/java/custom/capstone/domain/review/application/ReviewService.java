package custom.capstone.domain.review.application;

import custom.capstone.domain.review.dao.ReviewRepository;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.request.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.request.ReviewUpdateRequestDto;
import custom.capstone.domain.review.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(ReviewNotFoundException::new);

        review.update(requestDto.content());

        return reviewId;
    }

    /**
     * 후기 삭제
     */
    @Transactional
    public void deleteReview(final Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        reviewRepository.delete(review);
    }

    /**
     * 후기 조회
     */
    public Review findById(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
    }
}
