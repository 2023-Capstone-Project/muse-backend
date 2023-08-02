package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.AnswerRepository;
import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Answer;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.AnswerSaveRequestDto;
import custom.capstone.domain.inquiry.dto.request.AnswerUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.response.AnswerSaveResponseDto;
import custom.capstone.domain.inquiry.exception.AnswerNotFoundException;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final InquiryRepository inquiryRepository;
    private final AnswerRepository answerRepository;
    private final MemberRepository memberRepository;

    /**
     * 답변 등록
     */
    @Transactional
    public AnswerSaveResponseDto saveAnswer(final Long inquiryId, final AnswerSaveRequestDto requestDto) {
        getValidMember();

        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);

        final Answer answer = Answer.builder()
                .admin("관리자")
                .content(requestDto.content())
                .build();

        answerRepository.save(answer);
        inquiry.setAnswer(answer);

        return new AnswerSaveResponseDto(answer.getId());
    }

    /**
     * 답변 수정
     */
    @Transactional
    public Long updateAnswer(final Long inquiryId, final Long answerId, final AnswerUpdateRequestDto requestDto) {
        getValidMember();

        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);

        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        answer.update(requestDto.content());

        return answerId;
    }

    /**
     * 답변 삭제
     */
    public void deleteAnswer(final Long inquiryId, final Long answerId) {
        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);

        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        getValidMember();

        answerRepository.delete(answer);
    }

    // 관리자가 맞는지 확인
    private void getValidMember() {
        final String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        final Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        if (member.getRole() != MemberRole.ADMIN)
            throw new AccessDeniedException("해당 권한이 없습니다.");
    }
}
