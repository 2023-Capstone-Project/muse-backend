package custom.capstone.domain.interest.api;

import custom.capstone.domain.interest.application.InterestService;
import custom.capstone.domain.interest.dto.InterestDeleteResponseDto;
import custom.capstone.domain.interest.dto.InterestSaveRequestDto;
import custom.capstone.domain.interest.dto.InterestSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/interests")
@RequiredArgsConstructor
public class InterestApiController {
    private final InterestService interestService;

    /**
     * 좋아요 생성
     */
    @PostMapping
    public InterestSaveResponseDto saveInterest(@RequestBody @Valid InterestSaveRequestDto requestDto) {
        return new InterestSaveResponseDto(interestService.saveInterest(requestDto));
    }

    /**
     * 좋아요 취소
     */
    @DeleteMapping("/{interestId}")
    public InterestDeleteResponseDto cancelInterest(@PathVariable("interestId") final Long id) {
        interestService.cancelInterest(id);
        return new InterestDeleteResponseDto(id);
    }

}
