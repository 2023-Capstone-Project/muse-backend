package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberOccupation;
import custom.capstone.domain.members.domain.MemberStatus;
import custom.capstone.domain.members.dto.SignInMemberDto;
import custom.capstone.domain.members.dto.SignUpMemberDto;
import custom.capstone.domain.members.dto.UpdateMemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MembersService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(SignUpMemberDto signUpDto) {
        validateDuplicateMember(signUpDto);

        Member newMember = Member.of(
                signUpDto.name(),
                signUpDto.password(),
                signUpDto.email(),
                signUpDto.phoneNum(),
                MemberOccupation.NORMAL,
                MemberStatus.ACTIVE
        );
        memberRepository.save(newMember);

        return newMember.getId();
    }

    // 중복 회원 검사
    private void validateDuplicateMember(SignUpMemberDto signUpDto) {
        List<Member> findMembers = memberRepository.findByName(signUpDto.name());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 정보 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }


    /**
     * 회원 정보 수정
     */
    @Transactional
    public void update(Long id, UpdateMemberDto memberDto) {
        Member member = memberRepository.findOne(id);
        member.update(memberDto);
    }

    public Member login(SignInMemberDto request) {
        Member member = memberRepository.findOne(request.id());

        if(member == null || !member.getPassword().equals(request.password())) {
            return null;
        } else {
            return member;
        }
    }
}

