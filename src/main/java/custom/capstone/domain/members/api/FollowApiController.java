package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.FollowService;
import custom.capstone.domain.members.dto.FollowSaveRequestDto;
import custom.capstone.domain.members.dto.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class FollowApiController {
    private final FollowService followService;

    @PostMapping("/{id}")
    public Long saveFollow(@RequestBody FollowSaveRequestDto requestDto) {
        return followService.save(requestDto.fromId(), requestDto.toId());
    }

    @GetMapping("/{id}/followers")
    public Set<MemberProfileDto> findFollowers(@PathVariable Long id) {
        return followService.findFollowers(id);
    }

    @GetMapping("/{id}/followings")
    public Set<MemberProfileDto> findFollowings(@PathVariable Long id) {
        return followService.findFollowings(id);
    }

    @DeleteMapping("/{id}/followers")
    public Long deleteFollow(@PathVariable Long id) {
        followService.deleteFollow(id);
        return id;
    }
}
