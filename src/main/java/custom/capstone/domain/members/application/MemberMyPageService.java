package custom.capstone.domain.members.application;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.members.dao.MemberMyPageRepository;
import custom.capstone.domain.posts.application.PostImageService;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.response.PostListResponseDto;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.trading.domain.Trading;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberMyPageService {
    private final MemberMyPageRepository memberMyPageRepository;
    private final PostImageService postImageService;

    /**
     * 자신이 작성한 게시글 목록 페이징 조회
     */
    public Page<PostListResponseDto> findPostsByMemberId(final Long memberId, final Pageable pageable) {
        final Page<Post> posts = memberMyPageRepository.findPostsByMemberId(memberId, pageable);

        return posts.map(post -> {
            final String thumbnailUrl = postImageService.findThumbnailUrl(post);

            return new PostListResponseDto(post, thumbnailUrl);
        });
    }

    /**
     * 자신이 작성한 후기 목록 페이징 조회
     */
    public Page<Review> findReviewsByMemberId(final Long memberId, final Pageable pageable) {
        return memberMyPageRepository.findReviewsByMemberId(memberId, pageable);
    }

    /**
     * 자신이 거래한 시안 목록 페이징 조회
     */
    public Page<Trading> findTradingByMemberId(final Long memberId, final Pageable pageable) {
        return memberMyPageRepository.findTradingByMemberId(memberId, pageable);
    }

    /**
     * 자신이 좋아요한 시안 목록 페이징 조회
     */
    public Page<Interest> findInterestByMemberId(final Long memberId, final Pageable pageable) {
        return memberMyPageRepository.findInterestByMemberId(memberId, pageable);
    }
}
