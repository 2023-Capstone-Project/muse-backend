package custom.capstone.domain.interest.api;

import custom.capstone.domain.interest.application.InterestService;
import custom.capstone.domain.interest.dto.request.InterestDeleteRequestDto;
import custom.capstone.domain.interest.dto.request.InterestSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public Long saveInterest(@RequestBody @Valid final InterestSaveRequestDto requestDto) {
        return interestService.saveInterest(requestDto);
    }

    @Operation(summary = "좋아요 취소")
    @DeleteMapping
    public void cancelInterest(@RequestBody @Valid final InterestDeleteRequestDto requestDto) {
        interestService.cancelInterest(requestDto);
    }
}
