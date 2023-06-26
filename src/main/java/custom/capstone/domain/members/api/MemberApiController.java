package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.dto.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.MemberUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    // 수정 필요
    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    public Long saveMember(@RequestBody final MemberSaveRequestDto requestDto) {
        return memberService.saveMember(requestDto);
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/{memberId}/edit")
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