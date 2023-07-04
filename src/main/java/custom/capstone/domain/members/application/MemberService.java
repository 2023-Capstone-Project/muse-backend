package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.domain.MemberStatus;
import custom.capstone.domain.members.dto.MemberResponseDto;
import custom.capstone.domain.members.dto.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.request.MemberLoginRequestDto;
import custom.capstone.domain.members.dto.request.MemberUpdateRequestDto;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     */
    @Transactional
    public Long saveMember(final MemberSaveRequestDto requestDto) {
        validateSingUpRequest(requestDto);
//        checkPasswordEquals(requestDto);

        return memberRepository.save(Member.builder()
                .nickname(requestDto.nickname())
                .password(passwordEncoder.encode(requestDto.password()))
                .email(requestDto.email())
                .phoneNum(requestDto.phoneNum())
                .role(MemberRole.NORMAL)        //default 값은 일반인으로 설정
                .status(MemberStatus.ACTIVE)    //default 값은 활동중으로 설정
                .build()).getId();
    }

    /**
     * 로그인
     */
    public String login(final MemberLoginRequestDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.email())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 회원입니다."));

        // TODO: 비밀번호 확인 구현
//        if (!passwordEncoder.matches(requestDto.password(), member.getPassword()))
//            throw new IllegalArgumentException("비밀번호를 잘못 입력하였습니다.");

        List<String> roles = new ArrayList<>();
        roles.add(member.getRole().name());

        return jwtTokenProvider.createToken(requestDto.email(), roles);
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

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
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

    /**
     * 이메일, 닉네임 중복 여부 확인
     */
    private void checkEmailDuplicate(final MemberSaveRequestDto requestDto) {
        if (memberRepository.existsMemberByEmail(requestDto.email()))
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
    }

    private void checkNicknameDuplicate(final MemberSaveRequestDto requestDto) {
        if (memberRepository.existsMemberByNickname(requestDto.nickname()))
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
    }

    private void validateSingUpRequest(final MemberSaveRequestDto requestDto) {
        checkEmailDuplicate(requestDto);
        checkNicknameDuplicate(requestDto);
    }

    /**
     * 비밀번호 확인
     */
//    private void checkPasswordEquals(final MemberSaveRequestDto requestDto) {
//        if (!requestDto.password().equals(requestDto.checkPassword()))
//            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
//
//    }
}