package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.InquirySaveRequestDto;
import custom.capstone.domain.inquiry.dto.request.InquiryUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.response.InquirySaveResponseDto;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import custom.capstone.domain.inquiry.exception.InvalidInquiryException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final MemberRepository memberRepository;
    private final InquiryRepository inquiryRepository;

    /**
     * 문의 등록
     */
    @Transactional(readOnly = true)
    public InquirySaveResponseDto saveInquiry(final InquirySaveRequestDto requestDto) {
        final Member member = getValidMember();

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
    public Long updateInquiry(final Long inquiryId, final InquiryUpdateRequestDto requestDto) {
        final Inquiry inquiry = getValidInquiryMember(inquiryId);

        inquiry.update(requestDto.title(), requestDto.content());

        return inquiryId;
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
    public void deleteInquiry(final Long inquiryId) {
        final Inquiry inquiry = getValidInquiryMember(inquiryId);

        inquiryRepository.delete(inquiry);
    }

    // 작성자가 맞는지 확인
    private Member getValidMember() {
        final String email =  SecurityContextHolder.getContext().getAuthentication().getName();

        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    private Inquiry getValidInquiryMember(final Long inquiryId) {
        final Member member = getValidMember();

        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryNotFoundException::new);

        if (inquiry.getMember() != member)
            throw new InvalidInquiryException();

        return inquiry;
    }
}
