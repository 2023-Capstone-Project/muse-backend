package custom.capstone.domain.members.application;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import custom.capstone.domain.customization.MemberSaveCustomization;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Optional;

import static custom.capstone.domain.members.domain.MemberRole.ROLE_GENERAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private S3Uploader s3Uploader;

    @Nested
    @DisplayName("회원 가입")
    class Join {

        @DisplayName("[성공T] 회원 등록")
        @ParameterizedTest
        @AutoSource
        @Customization(MemberSaveCustomization.class)
        void saveMember_success(final MemberSaveRequestDto requestDto) throws IOException {
            // given -- 테스트의 상태 설정
            given(memberRepository.existsMemberByEmail(requestDto.email())).willReturn(false);
            given(memberRepository.existsMemberByNickname(requestDto.nickname())).willReturn(false);

            final MockMultipartFile image = new MockMultipartFile(
                    "profileImage",
                    "profile.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "muse profile content".getBytes()
            );

            final Member member = Member.builder()
                    .nickname(requestDto.nickname())
                    .profileImage(image.getName())
                    .email(requestDto.email())
                    .password(passwordEncoder.encode(requestDto.password()))
                    .phoneNumber(requestDto.phoneNumber())
                    .role(ROLE_GENERAL)
                    .status(MemberStatus.ACTIVE)
                    .build();

            given(memberRepository.save(any())).willReturn(member);

            // when -- 테스트 하고자 하는 행동
            final var result = memberService.saveMember(image, requestDto);

            // then -- 예상되는 변화 및 결과
            assertThat(result.getEmail()).isEqualTo(member.getEmail());
        }

        @DisplayName("[실패T] 중복된 이메일로 인한 회원가입 실패")
        @ParameterizedTest
        @AutoSource
        void saveMember_fail_duplicateEmail(final MemberSaveRequestDto requestDto) {
            // given -- 테스트의 상태 설정
            given(memberRepository.existsMemberByEmail(requestDto.email())).willReturn(true);

            final MockMultipartFile image = new MockMultipartFile(
                    "profileImage",
                    "profile.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "muse profile content".getBytes()
            );

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(MemberEmailExistException.class, () -> memberService.saveMember(image, requestDto));
        }

        @DisplayName("[실패T] 중복된 닉네임으로 인한 회원가입 실패")
        @ParameterizedTest
        @AutoSource
        void saveMember_fail_duplicateNickname(final MemberSaveRequestDto requestDto) {
            // given -- 테스트의 상태 설정
            given(memberRepository.existsMemberByNickname(requestDto.nickname())).willReturn(true);

            final MockMultipartFile image = new MockMultipartFile(
                    "profileImage",
                    "profile.jpg",
                    MediaType.IMAGE_JPEG_VALUE,
                    "muse profile content".getBytes()
            );

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(MemberNicknameExistException.class, () -> memberService.saveMember(image, requestDto));
        }
    }

    @Nested
    @DisplayName("로그인")
    class Login {

        @DisplayName("[성공T] 로그인 성공")
        @ParameterizedTest
        @AutoSource
        void login_success(final Member member) {
            // given -- 테스트의 상태 설정
            final var ACCESS_TOKEN = "accessToken";
            final var request = new MemberLoginRequestDto(member.getEmail(), member.getPassword());

            given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
            given(passwordEncoder.matches(any(), any())).willReturn(true);
            given(jwtTokenProvider.createToken(eq(member.getEmail()), eq(member.getRole().name()))).willReturn(ACCESS_TOKEN);

            final var response = new MemberLoginResponseDto(member.getId(), ACCESS_TOKEN);

            // when -- 테스트 하고자 하는 행동
            final var result = memberService.login(request);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.accessToken()).isEqualTo(ACCESS_TOKEN);
                softAssertions.assertThat(result.accessToken()).isEqualTo(response.accessToken());
            });
        }

        @DisplayName("[실패T] 존재하지 않는 혹은 쟐못된 이메일 입력으로 인한 로그인 실패")
        @ParameterizedTest
        @AutoSource
        void login_fail_nonexistentEmail(final MemberLoginRequestDto requestDto) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findByEmail(requestDto.email())).willThrow(LoginFailException.class);

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(LoginFailException.class, () -> memberService.login(requestDto));
        }

        @DisplayName("[실패T] 잘못된 비밀번호 입력으로 인한 로그인 실패")
        @ParameterizedTest
        @AutoSource
        void login_fail_incorrectPassword(final Member member, final MemberLoginRequestDto requestDto) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
            given(passwordEncoder.matches(requestDto.password(), member.getPassword())).willReturn(false);

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(PasswordException.class, () -> memberService.login(requestDto));
        }
    }

    @Nested
    @DisplayName("회원 정보 수정")
    class UpdateMember {

        @DisplayName("[성공T] 수정한 개인 정보로 최신화")
        @ParameterizedTest
        @AutoSource
        void updateMember_success(final Member member) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

            final var request = new MemberUpdateRequestDto(
                    "newmuse",
                    "newMuse123!",
                    "newMuse123!",
                    "010-5678-1234"
            );

            final var response = new MemberUpdateResponseDto(member.getId());

            // when -- 테스트 하고자 하는 행동
            final var result = memberService.updateMember(member.getEmail(), 1L, request);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.memberId()).isEqualTo(response.memberId());
                softAssertions.assertThat(member.getUpdatedAt()).isAfter(member.getCreatedAt());
            });
        }

        @DisplayName("[실패T] 인증되지 않은 사용자 접근으로 인한 회원 정보 수정 실패")
        @ParameterizedTest
        @AutoSource
        void updateMember_fail_unauthenticatedAccess(final MemberUpdateRequestDto requestDto) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findByEmail(any())).willThrow(UsernameNotFoundException.class);

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(UsernameNotFoundException.class, () -> memberService.updateMember(null, 1L, requestDto));
        }
    }

    @Nested
    @DisplayName("회원 조회")
    class FindMember {

        @DisplayName("[성공T] 자신의 회원 정보 조회")
        @ParameterizedTest
        @AutoSource
        void findMember_success(final Long memberId, final Member member) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findById(memberId)).willReturn(Optional.of(member));

            final var response = new MemberResponseDto(member);

            // when -- 테스트하고자 하는 행동
            final var result = memberService.findById(memberId);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getEmail()).isEqualTo(response.getEmail());
                softAssertions.assertThat(result.getNickname()).isEqualTo(response.getNickname());
            });
        }

        @DisplayName("[실패T] 존재하지 않는 회원ID로 인한 회원 조회 실패")
        @ParameterizedTest
        @AutoSource
        void findMember_fail_nonexistentId(final Long memberId) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findById(memberId)).willThrow(MemberNotFoundException.class);

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(MemberNotFoundException.class, () -> memberService.findById(memberId));
        }
    }
}