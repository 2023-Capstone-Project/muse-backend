package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.AnswerRepository;
import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Answer;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.AnswerSaveRequestDto;
import custom.capstone.domain.inquiry.dto.request.AnswerUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.response.AnswerSaveResponseDto;
import custom.capstone.domain.inquiry.dto.response.AnswerUpdateResponseDto;
import custom.capstone.domain.inquiry.exception.AnswerInvalidException;
import custom.capstone.domain.inquiry.exception.AnswerNotFoundException;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public AnswerSaveResponseDto saveAnswer(
            final String loginEmail,
            final AnswerSaveRequestDto requestDto
    ) {
        final Member admin = getValidAdmin(loginEmail);

        final Inquiry inquiry = inquiryRepository.findById(requestDto.inquiryId())
                .orElseThrow(InquiryNotFoundException::new);

        final Answer answer = Answer.builder()
                .admin(admin.getNickname())
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
            final String loginEmail,
            final Long answerId,
            final AnswerUpdateRequestDto requestDto
    ) {
        final Member admin = getValidAdmin(loginEmail);

        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        checkEqualAdmin(admin, answer);

        answer.update(requestDto.content());

        return new AnswerUpdateResponseDto(answerId);
    }

    /**
     * 답변 삭제
     */
    public void deleteAnswer(final String loginEmail, final Long answerId) {
        final Member admin = getValidAdmin(loginEmail);

        final Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        checkEqualAdmin(admin, answer);

        answerRepository.delete(answer);
    }

    // 관리자인지 확인
    private Member getValidAdmin(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));
    }

    // 답변한 관리자가 맞는지 확인
    private void checkEqualAdmin(final Member admin, final Answer answer) {
        if (!answer.getAdmin().equals(admin))
            throw new AnswerInvalidException();
    }
}
