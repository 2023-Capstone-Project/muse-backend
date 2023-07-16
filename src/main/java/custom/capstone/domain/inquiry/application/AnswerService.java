package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.AnswerRepository;
import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Answer;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.AnswerUpdateRequestDto;
import custom.capstone.domain.inquiry.exception.AnswerNotFoundException;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import lombok.RequiredArgsConstructor;
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
    public Answer saveAnswer(final Long inquiryId, final Answer answer) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);
        Answer save = answerRepository.save(answer);

        inquiry.setAnswer(save);
        return save;
    }

    /**
     * 답변 수정
     */
    @Transactional
    public Long updateAnswer(final Long inquiryId, final Long answerId, final AnswerUpdateRequestDto requestDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        inquiry.setAnswer(answer);
        answer.update(requestDto.content());

        return answerId;
    }

    /**
     * 답변 삭제
     */
    public void deleteAnswer(final Long inquiryId, final Long answerId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(AnswerNotFoundException::new);

        answerRepository.delete(answer);
    }
}
