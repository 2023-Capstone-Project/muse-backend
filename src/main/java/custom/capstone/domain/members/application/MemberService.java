package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.domain.MemberStatus;
import custom.capstone.domain.members.dto.MemberResponseDto;
import custom.capstone.domain.members.dto.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * TODO: 수정 필요
     */
    @Transactional
    public Long saveMember(final MemberSaveRequestDto requestDto) {
        Member member = Member.builder()
                .nickname(requestDto.nickname())
                .password(requestDto.password())
                .email(requestDto.email())
                .phoneNum(requestDto.phoneNum())
                .role(MemberRole.NORMAL)        //default 값은 일반인으로 설정
                .status(MemberStatus.ACTIVE)    //default 값은 활동중으로 설정
                .build();

        return memberRepository.save(member).getId();
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public Long updateMember(final Long memberId, final MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        member.update(requestDto.nickname(), requestDto.password(), requestDto.email(), requestDto.phoneNum());

        return memberId;
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteMember(final Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(memberRepository::delete);
    }

    /**
     * 회원 조회
     */
   public Member findById(final Long memberId) {
       return memberRepository.findById(memberId)
               .orElseThrow(NullPointerException::new);
   }

   public MemberResponseDto findDetailById(final Long memberId) {
       Member entity = memberRepository.findById(memberId)
               .orElseThrow(NullPointerException::new);

       return new MemberResponseDto(entity);
   }

    @Transactional
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

}