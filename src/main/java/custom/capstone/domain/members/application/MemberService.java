package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.domain.MemberStatus;
import custom.capstone.domain.members.dto.request.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.request.MemberLoginRequestDto;
import custom.capstone.domain.members.dto.request.MemberUpdateRequestDto;
import custom.capstone.domain.members.dto.response.MemberLoginResponseDto;
import custom.capstone.domain.members.dto.response.MemberResponseDto;
import custom.capstone.domain.members.dto.response.MemberUpdateResponseDto;
import custom.capstone.domain.members.exception.*;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MemberResponseDto saveMember(final MemberSaveRequestDto requestDto) {
        validateSingUpRequest(requestDto);
        checkPasswordEquals(requestDto.password(), requestDto.checkPassword());

        final Member member = memberRepository.save(Member.builder()
                .nickname(requestDto.nickname())
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .phoneNumber(requestDto.phoneNumber())
                .role(MemberRole.NORMAL)        //default 값은 일반인으로 설정
                .status(MemberStatus.ACTIVE)    //default 값은 활동중으로 설정
                .build());

        return new MemberResponseDto(member);
    }

    /**
     * 로그인
     */
    public MemberLoginResponseDto login(final MemberLoginRequestDto requestDto) {
        final Member member = memberRepository.findByEmail(requestDto.email())
                .orElseThrow(MemberNotFoundException::new);

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword()))
            throw new PasswordException();

        final List<String> roles = new ArrayList<>();
        roles.add(member.getRole().name());

        final String token = jwtTokenProvider.createToken(requestDto.email(), roles);

        return new MemberLoginResponseDto(member.getId(), token);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public MemberUpdateResponseDto updateMember(final Long memberId, final MemberUpdateRequestDto requestDto) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        checkPasswordEquals(requestDto.password(), requestDto.checkPassword());

        member.update(requestDto.nickname(), passwordEncoder.encode(requestDto.password()), requestDto.phoneNumber());

        return new MemberUpdateResponseDto(member.getId());
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteMember(final Long memberId) {
        final Optional<Member> member = memberRepository.findById(memberId);
        member.ifPresent(memberRepository::delete);
    }

    /**
     * 회원 조회
     */
    public Member findById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    public MemberResponseDto findDetailById(final Long memberId) {
        final Member entity = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

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
            throw new MemberEmailExistException();
    }

    private void checkNicknameDuplicate(final MemberSaveRequestDto requestDto) {
        if (memberRepository.existsMemberByNickname(requestDto.nickname()))
            throw new MemberNicknameExistException();
    }

    private void validateSingUpRequest(final MemberSaveRequestDto requestDto) {
        checkEmailDuplicate(requestDto);
        checkNicknameDuplicate(requestDto);
    }

    /**
     * 비밀번호 확인
     */
    private void checkPasswordEquals(final String password, final String checkPassword) {
        if (!password.equals(checkPassword))
            throw new JoinPasswordException();
    }

//    /**
//     * 로그인 확인
//     */
//    private void checkLoginUser(final Member member) {
//        if (!memberRepository.)
//    }
}