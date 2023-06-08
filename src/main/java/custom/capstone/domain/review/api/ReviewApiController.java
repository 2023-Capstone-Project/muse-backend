package custom.capstone.domain.review.api;

import custom.capstone.domain.review.application.ReviewService;
import custom.capstone.domain.review.dto.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.ReviewUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "후기 API")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @Operation(summary = "후기 생성")
    @PostMapping
    public Long saveReview(@RequestBody final ReviewSaveRequestDto requestDto) {
        return reviewService.saveReview(requestDto);
    }

    @Operation(summary = "후기 수정")
    @PatchMapping("/{reviewId}")
    public Long updateReview(@PathVariable("reviewId") final Long reviewId,
                             @RequestBody final ReviewUpdateRequestDto requestDto) {
        return reviewService.updateReview(reviewId, requestDto);
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("{reviewId}")
    public void deleteReview(@PathVariable("reviewId") final Long reviewId){
        reviewService.deleteReview(reviewId);
    }
 }
