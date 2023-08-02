package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.dto.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.request.MemberLoginRequestDto;
import custom.capstone.domain.members.dto.request.MemberUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @PostMapping("/join")
    public Long join(@Valid @RequestBody final MemberSaveRequestDto requestDto) {
        return memberService.saveMember(requestDto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public String login(@RequestBody final MemberLoginRequestDto requestDto) {
        return memberService.login(requestDto);
    }

    @Operation(summary = "로그아웃")
    @GetMapping("/logout")
    public String logout(final HttpServletRequest request, final HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/{memberId}")
    public Long updateMember(@PathVariable("memberId") final Long id,
                             @RequestBody final MemberUpdateRequestDto requestDto) {
        return memberService.updateMember(id, requestDto);
    }

    @Operation(summary = "회원 조회")
    @GetMapping("/{memberId}")
    public Member findById(@PathVariable("memberId") final Long id) {
        return memberService.findById(id);
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/{memberId}")
    public Long deleteMember(@PathVariable("memberId") final Long id) {
        memberService.deleteMember(id);
        return id;
    }
}