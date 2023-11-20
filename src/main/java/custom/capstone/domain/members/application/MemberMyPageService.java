package custom.capstone.domain.members.application;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.interest.dto.response.InterestListResponseDto;
import custom.capstone.domain.members.dao.MemberMyPageRepository;
import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.members.dto.response.*;
import custom.capstone.domain.posts.application.PostImageService;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.response.PostListResponseDto;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.response.ReviewListResponseDto;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.dto.response.TradingListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberMyPageService {
    private final MemberMyPageRepository memberMyPageRepository;
    private final MemberService memberService;
    private final PostImageService postImageService;

    /**
     * 자신이 작성한 게시글 목록 페이징 조회
     */
    public Page<MyPagePostResponseDto> findPostsByMemberId(final Long memberId, final Pageable pageable) {
        final MemberResponseDto member = memberService.findById(memberId);

        final Page<Post> posts = memberMyPageRepository.findPostsByMemberId(memberId, pageable);

        final MemberProfileDto memberProfileDto = new MemberProfileDto(
                memberId,
                member.getNickname(),
                member.getProfileImage()
        );

        final List<PostListResponseDto> postListResponseDtos = posts.getContent().stream()
                .map(post -> new PostListResponseDto(post, postImageService.findThumbnailUrl(post)))
                .collect(Collectors.toList());

        if (postListResponseDtos.isEmpty()) {
            return new PageImpl<>(Collections.singletonList(new MyPagePostResponseDto(memberProfileDto, Collections.emptyList())), pageable, 0);
        }

        return new PageImpl<>(Collections.singletonList(new MyPagePostResponseDto(memberProfileDto, postListResponseDtos)), pageable, posts.getTotalElements());
    }

    /**
     * 자신이 작성한 후기 목록 페이징 조회
     */
    public Page<MyPageReviewResponseDto> findReviewsByMemberId(final Long memberId, final Pageable pageable) {
        final MemberResponseDto member = memberService.findById(memberId);

        final Page<Review> reviews = memberMyPageRepository.findReviewsByMemberId(memberId, pageable);

        final MemberProfileDto memberProfileDto = new MemberProfileDto(
                memberId,
                member.getNickname(),
                member.getProfileImage()
        );

        final List<ReviewListResponseDto> reviewListResponseDtos = reviews.getContent().stream()
                .map(ReviewListResponseDto::new)
                .collect(Collectors.toList());

        if (reviewListResponseDtos.isEmpty()) {
            return new PageImpl<>(Collections.singletonList(new MyPageReviewResponseDto(memberProfileDto, Collections.emptyList())), pageable, 0);
        }

        return new PageImpl<>(Collections.singletonList(new MyPageReviewResponseDto(memberProfileDto, reviewListResponseDtos)), pageable, reviews.getTotalElements());
    }

    /**
     * 자신이 거래한 시안 목록 페이징 조회
     */
    public Page<MyPageTradingResponseDto> findTradingByMemberId(final Long memberId, final Pageable pageable) {
        final MemberResponseDto member = memberService.findById(memberId);

        final Page<Trading> tradings = memberMyPageRepository.findTradingByMemberId(memberId, pageable);

        final MemberProfileDto memberProfileDto = new MemberProfileDto(
                memberId,
                member.getNickname(),
                member.getProfileImage()
        );

        final List<TradingListResponseDto> tradingListResponseDtos = tradings.getContent().stream()
                .map(TradingListResponseDto::new)
                .collect(Collectors.toList());

        if (tradingListResponseDtos.isEmpty()) {
            return new PageImpl<>(Collections.singletonList(new MyPageTradingResponseDto(memberProfileDto, Collections.emptyList())), pageable, 0);
        }

        return new PageImpl<>(Collections.singletonList(new MyPageTradingResponseDto(memberProfileDto, tradingListResponseDtos)), pageable, tradings.getTotalElements());
    }

    /**
     * 자신이 좋아요한 시안 목록 페이징 조회
     */
    public Page<MyPageInterestResponseDto> findInterestByMemberId(final Long memberId, final Pageable pageable) {
        final MemberResponseDto member = memberService.findById(memberId);

        final Page<Interest> interests = memberMyPageRepository.findInterestByMemberId(memberId, pageable);

        final MemberProfileDto memberProfileDto = new MemberProfileDto(
                memberId,
                member.getNickname(),
                member.getProfileImage()
        );

        final List<InterestListResponseDto> interestListResponseDtos = interests.getContent().stream()
                .map(InterestListResponseDto::new)
                .collect(Collectors.toList());

        if (interestListResponseDtos.isEmpty()) {
            return new PageImpl<>(Collections.singletonList(new MyPageInterestResponseDto(memberProfileDto, Collections.emptyList())), pageable, 0);
        }

        return new PageImpl<>(Collections.singletonList(new MyPageInterestResponseDto(memberProfileDto, interestListResponseDtos)), pageable, interests.getTotalElements());
    }
}
