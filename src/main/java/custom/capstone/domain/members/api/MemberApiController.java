package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.dto.request.MemberLoginRequestDto;
import custom.capstone.domain.members.dto.request.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.request.MemberUpdateRequestDto;
import custom.capstone.domain.members.dto.response.MemberLoginResponseDto;
import custom.capstone.domain.members.dto.response.MemberResponseDto;
import custom.capstone.domain.members.dto.response.MemberUpdateResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @PostMapping(value = "/join", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<MemberResponseDto> join(
            @RequestPart(value = "profileImage", required = false) final MultipartFile image,
            @Valid @RequestPart("requestDto") final MemberSaveRequestDto requestDto
    ) throws IOException {
        final MemberResponseDto result = memberService.saveMember(image, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.JOIN_SUCCESS,
                result
        );
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public BaseResponse<MemberLoginResponseDto> login(@RequestBody final MemberLoginRequestDto requestDto) {
        final MemberLoginResponseDto result = memberService.login(requestDto);

        return BaseResponse.of(
                BaseResponseStatus.LOGIN_SUCCESS,
                result
        );
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public java.lang.String logout(final HttpServletRequest request, final HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/{memberId}")
    public BaseResponse<MemberUpdateResponseDto> updateMember(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("memberId") final Long id,
            @Valid @RequestBody final MemberUpdateRequestDto requestDto
    ) {
        final MemberUpdateResponseDto result = memberService.updateMember(loginEmail, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.MEMBER_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{memberId}")
    public BaseResponse<MemberResponseDto> findById(@PathVariable("memberId") final Long id) {
        final MemberResponseDto result = memberService.findById(id);

        return BaseResponse.of(
                BaseResponseStatus.MEMBER_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/{memberId}")
    public Long deleteMember(@PathVariable("memberId") final Long id) {
        memberService.deleteMember(id);
        return id;
    }
}