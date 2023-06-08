package custom.capstone.domain.interest.api;

import custom.capstone.domain.interest.application.InterestService;
import custom.capstone.domain.interest.dto.InterestDeleteResponseDto;
import custom.capstone.domain.interest.dto.InterestSaveRequestDto;
import custom.capstone.domain.interest.dto.InterestSaveResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "좋아요 API")
@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestApiController {
    private final InterestService interestService;

    @Operation(summary = "좋아요 생성")
    @PostMapping
    public InterestSaveResponseDto saveInterest(@RequestBody @Valid final InterestSaveRequestDto requestDto) {
        return new InterestSaveResponseDto(interestService.saveInterest(requestDto));
    }

    @Operation(summary = "좋아요 취소")
    @DeleteMapping("/{interestId}")
    public InterestDeleteResponseDto cancelInterest(@PathVariable("interestId") final Long id) {
        interestService.cancelInterest(id);
        return new InterestDeleteResponseDto(id);
    }
}
