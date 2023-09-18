package custom.capstone.domain.review.api;

import custom.capstone.domain.review.application.ReviewService;
import custom.capstone.domain.review.dto.request.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.request.ReviewUpdateRequestDto;
import custom.capstone.domain.review.dto.response.ReviewSaveResponseDto;
import custom.capstone.domain.review.dto.response.ReviewUpdateResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
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
    public BaseResponse<ReviewSaveResponseDto> saveReview(@RequestBody final ReviewSaveRequestDto requestDto) {
        final ReviewSaveResponseDto result = reviewService.saveReview(requestDto);

        return BaseResponse.of(
                BaseResponseStatus.REVIEW_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "후기 수정")
    @PatchMapping("/{reviewId}")
    public BaseResponse<ReviewUpdateResponseDto> updateReview(@PathVariable("reviewId") final Long reviewId,
                                                              @RequestBody final ReviewUpdateRequestDto requestDto) {
        final ReviewUpdateResponseDto result = reviewService.updateReview(reviewId, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.REVIEW_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("{reviewId}")
    public void deleteReview(@PathVariable("reviewId") final Long reviewId){
        reviewService.deleteReview(reviewId);
    }
}
