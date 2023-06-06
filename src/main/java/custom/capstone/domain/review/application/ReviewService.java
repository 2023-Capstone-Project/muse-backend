package custom.capstone.domain.review.application;

import custom.capstone.domain.review.dao.ReviewRepository;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.ReviewListResponseDto;
import custom.capstone.domain.review.dto.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public Long saveReview(ReviewSaveRequestDto requestDto) {
        Review review = Review.builder()
                .trading(requestDto.product())
                .member(requestDto.member())
                .content(requestDto.content())
                .build();

        return reviewRepository.save(review).getId();
    }

    @Transactional
    public Long updateReview(Long id, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 거래 상품은 존재하지 않습니다."));

        review.update(requestDto.content());

        return id;
    }

//    @Transactional
//    public List<ReviewListResponseDto> findReviewsByMemberId(Long id) {
//        return reviewRepository.findReviewsByMemberId(id)
//                .stream()
//                .map(ReviewListResponseDto::new)
//                .collect(Collectors.toList());
//    }

    @Transactional
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("거래 상품을 찾을 수 없습니다."));

        reviewRepository.delete(review);
    }
}
