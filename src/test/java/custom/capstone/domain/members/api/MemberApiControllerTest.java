package custom.capstone.domain.members.api;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.fasterxml.jackson.databind.ObjectMapper;
import custom.capstone.domain.customization.MemberCustomization;
import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.dto.request.MemberLoginRequestDto;
import custom.capstone.domain.members.dto.request.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.request.MemberUpdateRequestDto;
import custom.capstone.domain.members.dto.response.MemberLoginResponseDto;
import custom.capstone.domain.members.dto.response.MemberResponseDto;
import custom.capstone.domain.members.dto.response.MemberUpdateResponseDto;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import custom.capstone.infra.S3.S3Uploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static custom.capstone.domain.members.domain.MemberRole.ROLE_GENERAL;
import static custom.capstone.domain.members.domain.MemberStatus.ACTIVE;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Spy
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private S3Uploader s3Uploader;

    private final String ACCESS_TOKEN = "accessToken";

    @Nested
    @DisplayName("회원 가입")
    class Join {

        @DisplayName("[성공T] 회원 등록")
        @ParameterizedTest
        @AutoSource
        @Customization(MemberCustomization.class)
        void join_success(final MemberSaveRequestDto requestDto) throws Exception {
            // given -- 테스트의 상태 설정
            final MockMultipartFile image = new MockMultipartFile(
                    "profileImage",
                    "profile.jpg",
                    IMAGE_JPEG_VALUE,
                    "muse profile content".getBytes(UTF_8)
            );

            final Member member = Member.builder()
                    .nickname(requestDto.nickname())
                    .profileImage(image.getName())
                    .email(requestDto.email())
                    .password(passwordEncoder.encode(requestDto.password()))
                    .phoneNumber(requestDto.phoneNumber())
                    .role(ROLE_GENERAL)
                    .status(ACTIVE)
                    .build();

            final var request = objectMapper.writeValueAsString(requestDto);

            final var response = new MemberResponseDto(member);

            given(memberService.saveMember(any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    multipart("/api/members/join")
                            .file(image)
                            .file(new MockMultipartFile("requestDto", "", APPLICATION_JSON_VALUE, request.getBytes(UTF_8)))
                            .accept(APPLICATION_JSON_VALUE)
                            .contentType(MULTIPART_FORM_DATA_VALUE)
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("로그인")
    class Login {

        @DisplayName("[성공T] 로그인 성공")
        @ParameterizedTest
        @AutoSource
        void login_success(final Member member) throws Exception {
            // given -- 테스트의 상태 설정
            final var request = new MemberLoginRequestDto(member.getEmail(), member.getPassword());

            final var response = new MemberLoginResponseDto(member.getId(), ACCESS_TOKEN);

            given(memberService.login(request)).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    post("/api/members/login")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("회원 정보 수정")
    @WithMockUser(username = "muse@muse.com", roles = "GENERAL")
    class UpdateMember {

        @DisplayName("[성공T] 수정한 개인 정보로 최신화")
        @ParameterizedTest
        @AutoSource
        void updateMember_success(final Member member) throws Exception {
            // given -- 테스트의 상태 설정
            final var request = new MemberUpdateRequestDto(
                    "newmuse",
                    "newMuse123!",
                    "newMuse123!",
                    "010-5678-1234"
            );

            final var response = new MemberUpdateResponseDto(member.getId());

            given(memberService.updateMember(any(), any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    patch("/api/members/{memberId}", 1L)
                            .contentType(APPLICATION_JSON)
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .content(objectMapper.writeValueAsString(request))
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("회원 조회")
    @WithMockUser(username = "muse@muse.com", roles = "GENERAL")
    class FindMember {

        @DisplayName("[성공T] 자신의 회원 정보 조회")
        @ParameterizedTest
        @AutoSource
        void findMember_success(final Member member) throws Exception{
            // given -- 테스트의 상태 설정
            final var respone = new MemberResponseDto(member);

            given(memberService.findById(any())).willReturn(respone);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    get("/api/members/{memberId}", 1L)
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .contentType(APPLICATION_JSON)
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }
}