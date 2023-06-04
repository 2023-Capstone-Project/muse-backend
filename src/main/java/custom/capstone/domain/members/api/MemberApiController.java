package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.dto.MemberResponseDto;
import custom.capstone.domain.members.dto.MemberSaveRequestDto;
import custom.capstone.domain.members.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원 가입
     * TODO: 수정필요
     */
    @PostMapping("/signup")
    public Long saveMember(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.saveMember(requestDto);
    }

    /**
     * 회원 정보 수정
     */
    @PatchMapping("/{memberId}")
    public Long updateMember(@PathVariable("memberId") Long id,
                             @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.updateMember(id, requestDto);
    }

    /**
     * 회원 조회
     */
    @GetMapping("/{memberId}")
    public MemberResponseDto findMemberById(@PathVariable("memberId") Long id) {
        return memberService.findMemberById(id);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/{memberId}")
    public Long deleteMember(@PathVariable("memberId") Long id) {
        memberService.deleteMember(id);
        return id;
    }
}