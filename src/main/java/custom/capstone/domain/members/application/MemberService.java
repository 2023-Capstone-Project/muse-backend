package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.domain.MemberRole;
import custom.capstone.domain.members.domain.MemberStatus;
import custom.capstone.domain.members.dto.request.MemberLoginRequestDto;
import custom.capstone.domain.members.dto.request.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.request.MemberUpdateRequestDto;
import custom.capstone.domain.members.dto.response.MemberLoginResponseDto;
import custom.capstone.domain.members.dto.response.MemberResponseDto;
import custom.capstone.domain.members.dto.response.MemberUpdateResponseDto;
import custom.capstone.domain.members.exception.*;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import custom.capstone.infra.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    /**
     * 회원 가입
     */
    @Transactional
    public MemberResponseDto saveMember(final MultipartFile image, final MemberSaveRequestDto requestDto) throws IOException {
        validateSingUpRequest(requestDto);
        checkPasswordEquals(requestDto.password(), requestDto.checkPassword());

        final String profileImage = saveProfileImage(image);

        final Member member = Member.builder()
                .nickname(requestDto.nickname())
                .profileImage(profileImage)
                .email(requestDto.email())
                .password(passwordEncoder.encode(requestDto.password()))
                .phoneNumber(requestDto.phoneNumber())
                .role(MemberRole.ROLE_GENERAL)
                .status(MemberStatus.ACTIVE)
                .build();

        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    /**
     * 로그인
     */
    public MemberLoginResponseDto login(final MemberLoginRequestDto requestDto) {
        final Member member = memberRepository.findByEmail(requestDto.email())
                .orElseThrow(LoginFailException::new);

        if (!passwordEncoder.matches(requestDto.password(), member.getPassword()))
            throw new PasswordException();

        final String token = jwtTokenProvider.createToken(requestDto.email(), member.getRole().name());

        return new MemberLoginResponseDto(member.getId(), token);
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public MemberUpdateResponseDto updateMember(
            final String loginEmail,
            final Long id,
            final MemberUpdateRequestDto requestDto
    ) {
        final Member member = getValidMember(loginEmail);

        checkNicknameDuplicate(member.getNickname());
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
    public MemberResponseDto findById(final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        return new MemberResponseDto(member);
    }

    public Member findByEmail(final String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    // 회원인지 확인
    private Member getValidMember(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
    
     // 이메일, 닉네임 중복 여부 확인
     private void checkEmailDuplicate(final String email) {
         if (memberRepository.existsMemberByEmail(email))
             throw new MemberEmailExistException();
     }

    private void checkNicknameDuplicate(final String nickname) {
        if (memberRepository.existsMemberByNickname(nickname))
            throw new MemberNicknameExistException();
    }

    private void validateSingUpRequest(final MemberSaveRequestDto requestDto) {
        checkEmailDuplicate(requestDto.email());
        checkNicknameDuplicate(requestDto.nickname());
    }

    // 비밀번호 확인
    private void checkPasswordEquals(final String password, final String checkPassword) {
        if (!password.equals(checkPassword))
            throw new JoinPasswordException();
    }

    private String saveProfileImage(final MultipartFile image) throws IOException {
        final String folderPath = "member-profile/";

        return s3Uploader.uploadImage(image, folderPath);
    }
}