package custom.capstone.domain.members.application;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRepository;
import custom.capstone.domain.members.dto.UpdateMemberEmailRequestDto;
import custom.capstone.domain.members.dto.UpdateMemberNameRequestDto;
import custom.capstone.domain.members.dto.UpdateMemberPasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검사
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 이름 수정
     */
    @Transactional
    public void updateName(final Long memberId, final UpdateMemberNameRequestDto update) {
        final Member member = findById(memberId);
        member.updateName(update.getName());
    }

    /**
     * 회원 비밀번호 수정
     */
    @Transactional
    public void updatePassword(final Long memberId, final UpdateMemberPasswordRequestDto update) {
        final Member member = findById(memberId);
        member.updatePassword(update.getPassword());
    }

    /**
     * 회원 이메일 수정
     */
    @Transactional
    public void updateEmail(final Long memberId, final UpdateMemberEmailRequestDto update) {
        final Member member = findById(memberId);
        member.updateEmail(update.getEmail());
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 회원정보 조회
     */
    public Member findById(final Long id) {
        return memberRepository.findById(id).orElseThrow(NullPointerException::new);
    }
}
