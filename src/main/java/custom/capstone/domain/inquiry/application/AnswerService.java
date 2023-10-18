package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.AnswerRepository;
import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Answer;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.AnswerSaveRequestDto;
import custom.capstone.domain.inquiry.dto.request.AnswerUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.response.AnswerSaveResponseDto;
import custom.capstone.domain.inquiry.dto.response.AnswerUpdateResponseDto;
import custom.capstone.domain.inquiry.exception.AnswerNotFoundException;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import custom.capstone.domain.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final InquiryRepository inquiryRepository;
    private final AnswerRepository answerRepository;

    /**
     * 답변 등록
     */
    @Transactional
    public AnswerSaveResponseDto saveAnswer(
            final Member member,
            final Long inquiryId,
            final AnswerSaveRequestDto requestDto
    ) {
        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);

        final Answer answer = Answer.builder()
                .admin(member.getEmail())
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
    public AnswerUpdateResponseDto updateAnswer(
            final Member member,
            final Long answerId,
            final AnswerUpdateRequestDto requestDto
    ) {
        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        checkEqualMember(member, answer);

        answer.update(requestDto.content());

        return new AnswerUpdateResponseDto(answerId);
    }

    /**
     * 답변 삭제
     */
    public void deleteAnswer(final Member member, final Long answerId) {
        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        checkEqualMember(member, answer);

        answerRepository.delete(answer);
    }

    // 관리자가 맞는지 확인
    private void checkEqualMember(final Member member, final Answer answer) {
        if (!answer.getAdmin().equals(member.getEmail()))
            throw new AccessDeniedException("해당 권한이 없습니다.");
    }
}
