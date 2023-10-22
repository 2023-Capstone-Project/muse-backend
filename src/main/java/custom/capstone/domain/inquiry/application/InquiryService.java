package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.request.InquirySaveRequestDto;
import custom.capstone.domain.inquiry.dto.request.InquiryUpdateRequestDto;
import custom.capstone.domain.inquiry.dto.response.InquirySaveResponseDto;
import custom.capstone.domain.inquiry.dto.response.InquiryUpdateResponseDto;
import custom.capstone.domain.inquiry.exception.InquiryInvalidException;
import custom.capstone.domain.inquiry.exception.InquiryNotFoundException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    /**
     * 문의 등록
     */
    @Transactional(readOnly = true)
    public InquirySaveResponseDto saveInquiry(final String loginEmail, final InquirySaveRequestDto requestDto) {
        final Member member = getValidMember(loginEmail);

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
            final String loginEmail,
            final Long inquiryId,
            final InquiryUpdateRequestDto requestDto
    ) {
        final Member member = getValidMember(loginEmail);

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
    public void deleteInquiry(final String loginEmail, final Long inquiryId) {
        final Member member = getValidMember(loginEmail);

        final Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(InquiryInvalidException::new);

        checkEqualMember(member, inquiry);

        inquiryRepository.delete(inquiry);
    }

    // 회원인지 확인
    private Member getValidMember(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 작성자가 맞는지 확인
    private void checkEqualMember(final Member member, final Inquiry inquiry) {
        if (!inquiry.getMember().getId().equals(member.getId()))
            throw new InquiryInvalidException();
    }
}
