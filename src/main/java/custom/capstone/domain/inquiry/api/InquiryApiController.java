package custom.capstone.domain.inquiry.api;

import custom.capstone.domain.inquiry.application.AnswerService;
import custom.capstone.domain.inquiry.application.InquiryService;
import custom.capstone.domain.inquiry.domain.Answer;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.AnswerUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.InquiryUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객센터 API")
@RestController
@RequestMapping("/api/inquires")
@RequiredArgsConstructor
public class InquiryApiController {
    private final InquiryService inquiryService;
    private final AnswerService answerService;

    @Operation(summary = "1:1 문의 등록")
    @PostMapping("/write")
    public Inquiry saveInquiry(final Inquiry inquiry) {
        return inquiryService.saveInquiry(inquiry);
    }

    @Operation(summary = "1:1 문의 수정")
    @PatchMapping("/{inquiryId}/edit")
    public Long updateInquiry(@PathVariable("inquiryId") final Long id,
                              @RequestBody final InquiryUpdateRequestDto requestDto) {
        return inquiryService.updateInquiry(id, requestDto);
    }

    @Operation(summary = "자주하는 질문 조회")
    @GetMapping
    public Page<Inquiry> findAll(final Pageable pageable) {
        return inquiryService.findAll(pageable);
    }

    @Operation(summary = "문의 삭제")
    @DeleteMapping("{inquiryId}")
    public Long deleteInquiry(@PathVariable("inquiryId") final Long id) {
        inquiryService.deleteInquiry(id);
        return id;
    }

    @Operation(summary = "1:1 문의 답변 등록")
    @PostMapping("/{inquiryId}/answer")
    public Answer saveAnswer(@PathVariable("inquiryId") final Long id,
                             @RequestBody final Answer answer) {
        return answerService.saveAnswer(id, answer);
    }

    @Operation(summary = "1:1 문의 답변 수정")
    @PatchMapping("/{inquiryId}/answer/{answerId}/edit")
    public Long updateAnswer(@PathVariable("inquiryId") final Long inquiryId,
                             @PathVariable("answerId") final Long answerId,
                             @RequestBody AnswerUpdateRequestDto requestDto) {
        return answerService.updateAnswer(inquiryId, answerId, requestDto);
    }

    @Operation(summary = "1:1 문의 답변 삭제")
    @DeleteMapping("/{inquiryId}/answer/{answerId}")
    public Long deleteAnswer(@PathVariable("inquiryId") final Long inquiryId,
                             @PathVariable("answerId") final Long answerId) {
        answerService.deleteAnswer(inquiryId, answerId);
        return answerId;
    }
}
