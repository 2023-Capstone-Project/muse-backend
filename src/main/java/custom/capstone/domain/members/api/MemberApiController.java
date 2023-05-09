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

    @PostMapping("/signup")
    public Long saveMember(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.saveMember(requestDto);
    }

    @PatchMapping("/{id}")
    public Long updateMember(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.updateMember(id, requestDto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto findMemberById(@PathVariable Long id) {
        return memberService.findMemberById(id);
    }

    @DeleteMapping("/{id}")
    public Long deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return id;
    }
}