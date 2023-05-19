package custom.capstone.domain.trading.api;

import custom.capstone.domain.trading.application.TradingReviewService;
import custom.capstone.domain.trading.dto.TradingReviewListResponseDto;
import custom.capstone.domain.trading.dto.TradingReviewSaveRequestDto;
import custom.capstone.domain.trading.dto.TradingReviewUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class TradingReviewApiController {
    private final TradingReviewService reviewService;

    @PostMapping
    public Long saveReview(@RequestBody TradingReviewSaveRequestDto requestDto) {
        return reviewService.saveReview(requestDto);
    }

    @PatchMapping("/{reviewId}")
    public Long updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody TradingReviewUpdateRequestDto requestDto) {
        return reviewService.updateReview(reviewId, requestDto);
    }

    @GetMapping("/{memberId}")
    public List<TradingReviewListResponseDto> findReviewByMemberId(@PathVariable("memberId") Long memberId) {
        return reviewService.findReviewByMemberId(memberId);
    }

    @DeleteMapping("{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
    }
 }
