package custom.capstone.domain.inquiry.application;

import custom.capstone.domain.inquiry.dao.InquiryRepository;
import custom.capstone.domain.inquiry.domain.Inquiry;
import custom.capstone.domain.inquiry.dto.InquiryUpdateRequestDto;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
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
    public Inquiry saveInquiry(final Inquiry inquiry) {
        Member member = getValidMember();
        inquiry.setMember(member);

        return inquiryRepository.save(inquiry);
    }

    /**
     * 문의 수정
     */
    @Transactional
    public Long updateInquiry(final Long inquiryId, final InquiryUpdateRequestDto requestDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(IllegalAccessError::new);

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
        Member member = getValidMember();
        Inquiry inquiry = inquiryRepository.findByIdWithAnswer(inquiryId)
                .orElseThrow(IllegalArgumentException::new);

        if (inquiry.getMember() != member) {
            throw new IllegalArgumentException("해당 문의는 삭제할 수 없습니다.");
        }

        inquiryRepository.delete(inquiry);
    }

    // 작성자가 맞는지 확인
    private Member getValidMember() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(IllegalArgumentException::new);
    }
}
