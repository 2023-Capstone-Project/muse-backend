package custom.capstone.domain.inquiry.api;

import custom.capstone.domain.inquiry.application.AnswerService;
import custom.capstone.domain.inquiry.application.InquiryService;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.*;
import custom.capstone.domain.inquiry.dto.response.AnswerSaveResponseDto;
import custom.capstone.domain.inquiry.dto.response.AnswerUpdateResponseDto;
import custom.capstone.domain.inquiry.dto.response.InquirySaveResponseDto;
import custom.capstone.domain.inquiry.dto.response.InquiryUpdateResponseDto;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "고객센터 API")
@RestController
@RequestMapping("/api/inquires")
@RequiredArgsConstructor
public class InquiryApiController {
    private final InquiryService inquiryService;
    private final AnswerService answerService;

    @Operation(summary = "1:1 문의 등록")
    @PostMapping
    public BaseResponse<InquirySaveResponseDto> saveInquiry(
            @AuthenticationPrincipal final Member member,
            final InquirySaveRequestDto requestDto
    ) {
        final InquirySaveResponseDto result = inquiryService.saveInquiry(member, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.INQUIRY_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "1:1 문의 수정")
    @PatchMapping("/{inquiryId}")
    public BaseResponse<InquiryUpdateResponseDto> updateInquiry(
            @AuthenticationPrincipal final Member member,
            @PathVariable("inquiryId") final Long id,
            @RequestBody final InquiryUpdateRequestDto requestDto
    ) {
        final InquiryUpdateResponseDto result = inquiryService.updateInquiry(member, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.INQUIRY_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "자주하는 질문 조회")
    @GetMapping
    public Page<Inquiry> findAll(final Pageable pageable) {
        return inquiryService.findAll(pageable);
    }

    @Operation(summary = "문의 삭제")
    @DeleteMapping("/{inquiryId}")
    public Long deleteInquiry(
            @AuthenticationPrincipal final Member member,
            @PathVariable("inquiryId") final Long id
    ) {
        inquiryService.deleteInquiry(member, id);
        return id;
    }

    @Operation(summary = "1:1 문의 답변 등록")
    @PostMapping("/answer")
    public BaseResponse<AnswerSaveResponseDto> saveAnswer(
            @AuthenticationPrincipal final Member member,
            @PathVariable("inquiryId") final Long id,
            @RequestBody final AnswerSaveRequestDto requestDto
    ) {
        final AnswerSaveResponseDto result = answerService.saveAnswer(member, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.ANSWER_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "1:1 문의 답변 수정")
    @PatchMapping("/answer/{answerId}")
    public BaseResponse<AnswerUpdateResponseDto> updateAnswer(
            @AuthenticationPrincipal final Member member,
            @PathVariable("answerId") final Long answerId,
            @RequestBody final AnswerUpdateRequestDto requestDto
    ) {
        final AnswerUpdateResponseDto result = answerService.updateAnswer(member, answerId, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.ANSWER_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "1:1 문의 답변 삭제")
    @DeleteMapping("/answer/{answerId}")
    public Long deleteAnswer(
            @AuthenticationPrincipal final Member member,
            @PathVariable("answerId") final Long answerId
    ) {
        answerService.deleteAnswer(member, answerId);
        return answerId;
    }
}
