package custom.capstone.domain.review.api;

import custom.capstone.domain.review.application.ReviewService;
import custom.capstone.domain.review.dto.ReviewListResponseDto;
import custom.capstone.domain.review.dto.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.ReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @PostMapping
    public Long saveReview(@RequestBody ReviewSaveRequestDto requestDto) {
        return reviewService.saveReview(requestDto);
    }

    @PatchMapping("/{reviewId}")
    public Long updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewUpdateRequestDto requestDto) {
        return reviewService.updateReview(reviewId, requestDto);
    }

//    @GetMapping("/{memberId}")
//    public List<ReviewListResponseDto> findReviewsByMemberId(@PathVariable("memberId") Long memberId) {
//        return reviewService.findReviewsByMemberId(memberId);
//    }

    @DeleteMapping("{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
    }
 }
