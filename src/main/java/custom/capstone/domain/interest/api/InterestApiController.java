package custom.capstone.domain.interest.api;

import custom.capstone.domain.interest.application.InterestService;
import custom.capstone.domain.interest.dto.request.InterestDeleteRequestDto;
import custom.capstone.domain.interest.dto.request.InterestSaveRequestDto;
import custom.capstone.domain.interest.dto.response.InterestSaveResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "좋아요 API")
@RestController
@RequestMapping("/api/interests")
@RequiredArgsConstructor
public class InterestApiController {
    private final InterestService interestService;

    @Operation(summary = "좋아요 생성")
    @PostMapping
    public BaseResponse<InterestSaveResponseDto> saveInterest(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody @Valid final InterestSaveRequestDto requestDto
    ) {
        final InterestSaveResponseDto result = interestService.saveInterest(loginEmail, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.INTEREST_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "좋아요 취소")
    @DeleteMapping
    public void cancelInterest(
            @AuthenticationPrincipal final String loginEmail,
            @RequestBody @Valid final InterestDeleteRequestDto requestDto
    ) {
        interestService.cancelInterest(loginEmail, requestDto);
    }
}
