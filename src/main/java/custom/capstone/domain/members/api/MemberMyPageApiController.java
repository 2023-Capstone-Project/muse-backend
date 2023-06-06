package custom.capstone.domain.members.api;

import custom.capstone.domain.interest.dto.InterestListResponseDto;
import custom.capstone.domain.members.application.FollowService;
import custom.capstone.domain.members.application.MemberMyPageService;
import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.posts.dto.PostListResponseDto;
import custom.capstone.domain.trading.dto.TradingListResponseDto;
import custom.capstone.domain.review.dto.ReviewListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberMyPageApiController {
    private final MemberMyPageService memberMyPageService;
    private final FollowService followService;

    /**
     * 자신이 작성한 게시글 목록 페이징 조회
     */
    @GetMapping("/{memberId}/mypage/posts")
    public Page<PostListResponseDto> postsByMember(@PathVariable("memberId") final Long id, final Pageable pageable) {
        return memberMyPageService.findPostsByMemberId(id, pageable)
                .map(PostListResponseDto::new);
    }

    /**
     * 자신이 작성한 후기 목록 페이징 조회
     */
    @GetMapping("/{memberId}/mypage/reviews")
    public Page<ReviewListResponseDto> reviewsByMember(@PathVariable("memberId") final Long id, final Pageable pageable) {
        return memberMyPageService.findReviewsByMemberId(id, pageable)
                .map(ReviewListResponseDto::new);
    }

    /**
     * 자신이 거래한 시안 목록 페이징 조회
     */
    @GetMapping("/{memberId}/mypage/trading")
    public Page<TradingListResponseDto> productsByMember(@PathVariable("memberId") final Long id, final Pageable pageable) {
        return memberMyPageService.findTradingByMemberId(id, pageable)
                .map(TradingListResponseDto::new);
    }

    /**
     * 자신이 좋아요한 시안 목록 페이징 조회
     */
    @GetMapping("/{memberId}/mypage/interest")
    public Page<InterestListResponseDto> interestByMember(@PathVariable("memberId") final Long id, final Pageable pageable) {
        return memberMyPageService.findInterestByMemberId(id, pageable)
                .map(InterestListResponseDto::new);
    }

    /**
     * 팔로워 조회
     */
    @GetMapping("/{memberId}/mypage/followers")
    public Set<MemberProfileDto> findFollowers(@PathVariable("memberId") final Long id) {
        return followService.findFollowers(id);
    }

    /**
     * 팔로잉 조회
     */
    @GetMapping("/{memberId}/mypage/followings")
    public Set<MemberProfileDto> findFollowings(@PathVariable("memberId") final Long id) {
        return followService.findFollowings(id);
    }

    /**
     * 팔로우 끊기
     */
    @DeleteMapping("/{memberId}/mypage/followers")
    public Long deleteFollow(@PathVariable("memberId") final Long id) {
        followService.deleteFollow(id);
        return id;
    }
}
