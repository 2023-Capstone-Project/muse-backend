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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "후기 API")
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApiController {
    private final ReviewService reviewService;

    @Operation(summary = "후기 생성")
    @PostMapping
    public BaseResponse<ReviewSaveResponseDto> saveReview(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody final ReviewSaveRequestDto requestDto
    ) {
        final ReviewSaveResponseDto result = reviewService.saveReview(loginEmail, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.REVIEW_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "후기 수정")
    @PatchMapping("/{reviewId}")
    public BaseResponse<ReviewUpdateResponseDto> updateReview(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("reviewId") final Long id,
            @RequestBody final ReviewUpdateRequestDto requestDto
    ) {
        final ReviewUpdateResponseDto result = reviewService.updateReview(loginEmail, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.REVIEW_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("{reviewId}")
    public void deleteReview(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("reviewId") final Long id
    ){
        reviewService.deleteReview(loginEmail, id);
    }
}
