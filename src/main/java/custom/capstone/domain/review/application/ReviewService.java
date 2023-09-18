package custom.capstone.domain.review.application;

import custom.capstone.domain.review.dao.ReviewRepository;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.request.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.request.ReviewUpdateRequestDto;
import custom.capstone.domain.review.dto.response.ReviewSaveResponseDto;
import custom.capstone.domain.review.dto.response.ReviewUpdateResponseDto;
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
    public ReviewSaveResponseDto saveReview(final ReviewSaveRequestDto requestDto) {
        final Review review = Review.builder()
                .trading(requestDto.product())
                .member(requestDto.member())
                .content(requestDto.content())
                .build();

        reviewRepository.save(review);

        return new ReviewSaveResponseDto(review.getId());
    }

    /**
     * 후기 수정
     */
    @Transactional
    public ReviewUpdateResponseDto updateReview(final Long reviewId, final ReviewUpdateRequestDto requestDto) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        review.update(requestDto.content());

        return new ReviewUpdateResponseDto(reviewId);
    }

    /**
     * 후기 조회
     */
    public Review findById(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
    }

    /**
     * 후기 삭제
     */
    @Transactional
    public void deleteReview(final Long reviewId) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        reviewRepository.delete(review);
    }
}
