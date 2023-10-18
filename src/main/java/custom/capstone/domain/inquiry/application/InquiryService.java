package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.InquirySaveRequestDto;
import custom.capstone.domain.inquiry.dto.request.InquiryUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.response.InquirySaveResponseDto;
import custom.capstone.domain.inquiry.dto.response.InquiryUpdateResponseDto;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import custom.capstone.domain.inquiry.exception.InquiryInvalidException;
import custom.capstone.domain.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;

    /**
     * 문의 등록
     */
    @Transactional(readOnly = true)
    public InquirySaveResponseDto saveInquiry(final Member member, final InquirySaveRequestDto requestDto) {

        final Inquiry inquiry = Inquiry.builder()
                .member(member)
                .title(requestDto.title())
                .content(requestDto.content())
                .build();

        inquiryRepository.save(inquiry);

        return new InquirySaveResponseDto(inquiry.getId());
    }

    /**
     * 문의 수정
     */
    @Transactional
    public InquiryUpdateResponseDto updateInquiry(
            final Member member,
            final Long inquiryId,
            final InquiryUpdateRequestDto requestDto
    ) {
        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                        .orElseThrow(InquiryNotFoundException::new);

        checkEqualMember(member, inquiry);

        inquiry.update(requestDto.title(), requestDto.content());

        return new InquiryUpdateResponseDto(inquiryId);
    }

    /**
     * 모든 문의 조회
     */
    @Transactional
    public Page<Inquiry> findAll(final Pageable pageable) {
        return inquiryRepository.findAll(pageable);
    }

    /**
     * 문의 삭제
     */
    @Transactional
    public void deleteInquiry(final Member member, final Long inquiryId) {
        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryInvalidException::new);

        checkEqualMember(member, inquiry);

        inquiryRepository.delete(inquiry);
    }

    // 작성자가 맞는지 확인
    private void checkEqualMember(final Member member, final Inquiry inquiry) {
        if (!inquiry.getMember().getId().equals(member.getId()))
            throw new InquiryInvalidException();
    }
}
