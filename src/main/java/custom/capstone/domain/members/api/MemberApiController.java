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
    public Long save(@RequestBody MemberSaveRequestDto requestDto) {
        return memberService.save(requestDto);
    }

    @PatchMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody MemberUpdateRequestDto requestDto) {
        return memberService.update(id, requestDto);
    }

    @GetMapping("/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        memberService.delete(id);
        return id;
    }
}