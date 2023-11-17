package custom.capstone.domain.members.api;

import custom.capstone.domain.members.application.FollowService;
import custom.capstone.domain.members.application.MemberMyPageService;
import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.members.dto.response.MyPageInterestResponseDto;
import custom.capstone.domain.members.dto.response.MyPagePostResponseDto;
import custom.capstone.domain.members.dto.response.MyPageReviewResponseDto;
import custom.capstone.domain.members.dto.response.MyPageTradingResponseDto;
import custom.capstone.domain.posts.application.PostImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "마이페이지 API")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberMyPageApiController {
    private final MemberMyPageService memberMyPageService;
    private final FollowService followService;
    private final PostImageService postImageService;

    @Operation(summary = "자신이 작성한 게시글 목록 페이징 조회")
    @GetMapping("/{memberId}/mypage/posts")
    public Page<MyPagePostResponseDto> postsByMember(
            @PathVariable("memberId") final Long id,
            @PageableDefault(size = 20) final Pageable pageable
    ) {
        return memberMyPageService.findPostsByMemberId(id, pageable);
    }

    @Operation(summary = "자신이 작성한 후기 목록 페이징 조회")
    @GetMapping("/{memberId}/mypage/reviews")
    public Page<MyPageReviewResponseDto> reviewsByMember(
            @PathVariable("memberId") final Long id,
            @PageableDefault(size = 20) final Pageable pageable
    ) {
        return memberMyPageService.findReviewsByMemberId(id, pageable);
    }

    @Operation(summary = "자신이 거래한 시안 목록 페이징 조회")
    @GetMapping("/{memberId}/mypage/trading")
    public Page<MyPageTradingResponseDto> productsByMember(
            @PathVariable("memberId") final Long id,
            @PageableDefault(size = 20) final Pageable pageable
    ) {
        return memberMyPageService.findTradingByMemberId(id, pageable);
    }

    @Operation(summary = "자신이 좋아요한 시안 목록 페이징 조회")
    @GetMapping("/{memberId}/mypage/interest")
    public Page<MyPageInterestResponseDto> interestByMember(
            @PathVariable("memberId") final Long id,
            @PageableDefault(size = 20) final Pageable pageable
    ) {
        return memberMyPageService.findInterestByMemberId(id, pageable);
    }

    @Operation(summary = "팔로워 조회")
    @GetMapping("/{memberId}/mypage/followers")
    public Set<MemberProfileDto> findFollowers(@PathVariable("memberId") final Long id) {
        return followService.findFollowers(id);
    }

    @Operation(summary = "팔로잉 조회")
    @GetMapping("/{memberId}/mypage/followings")
    public Set<MemberProfileDto> findFollowings(@PathVariable("memberId") final Long id) {
        return followService.findFollowings(id);
    }

    @Operation(summary = "팔로워 끊기")
    @DeleteMapping("/{memberId}/mypage/followers")
    public Long deleteFollow(@PathVariable("memberId") final Long id) {
        followService.deleteFollow(id);
        return id;
    }
}
