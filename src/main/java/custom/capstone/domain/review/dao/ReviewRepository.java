package custom.capstone.domain.review.dao;

import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.ReviewListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    List<ReviewListResponseDto> findReviewsByMemberId(final Long memberId);
}
