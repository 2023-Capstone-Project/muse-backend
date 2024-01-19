package custom.capstone.domain.members.application;

import autoparams.AutoSource;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.members.dao.MemberMyPageRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.dto.response.MemberResponseDto;
import custom.capstone.domain.posts.application.PostImageService;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostType;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.trading.domain.Trading;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberMyPageServiceTest {

    @InjectMocks
    private MemberMyPageService memberMyPageService;

    @Mock
    private MemberMyPageRepository memberMyPageRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private PostImageService postImageService;

    @Nested
    @DisplayName("마이 페이지")
    class FindMyPage {

        @DisplayName("[성공T] 자신이 작성한 게시글 목록 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void find_success_my_posts(final Member member, final Category category) throws Exception {
            // given
            given(memberService.findById(any())).willReturn(new MemberResponseDto(member));

            final List<Post> posts = new ArrayList<>();
            posts.add(new Post("test1", "test1", 1000, member, category, PostType.CUSTOM));
            posts.add(new Post("test2", "test2", 2000, member, category, PostType.CUSTOM));

            given(postImageService.findThumbnailUrl(any())).willReturn("thumbnail");

            final Pageable pageable = PageRequest.of(0, posts.size(), Sort.by(Sort.Direction.DESC, "createdAt"));

            final var response = new PageImpl<>(posts, pageable, posts.size());

            given(memberMyPageRepository.findPostsByMemberId(any(), any())).willReturn(response);

            // when
            final var result = memberMyPageService.findPostsByMemberId(1L, pageable);

            // then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalElements()).isEqualTo(response.getTotalElements());
                softAssertions.assertThat(result.getTotalPages()).isEqualTo(response.getTotalPages());
            });
        }

        @DisplayName("[성공T] 자신이 작성한 후기 목록 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void find_success_my_reviews(final Member member, final Trading trading) throws Exception {
            // given -- 테스트의 상태 설정
            given(memberService.findById(any())).willReturn(new MemberResponseDto(member));

            final List<Review> reviews = new ArrayList<>();
            reviews.add(new Review(trading, member, "Good 😀"));
            reviews.add(new Review(trading, member, "Bad 🙁"));

            final Pageable pageable = PageRequest.of(0, reviews.size(), Sort.by(Sort.Direction.DESC, "createdAt"));

            final var response = new PageImpl<>(reviews, pageable, reviews.size());

            given(memberMyPageRepository.findReviewsByMemberId(any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = memberMyPageService.findReviewsByMemberId(1L, pageable);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalElements()).isEqualTo(response.getTotalElements());
                softAssertions.assertThat(result.getTotalPages()).isEqualTo(response.getTotalPages());
            });
        }

        @DisplayName("[성공T] 자신이 거래한 시안 목록 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void find_success_my_tradings(
                final Member buyer,
                final Member seller,
                final Post post
        ) throws Exception {

            // given -- 테스트의 상태 설정
            given(memberService.findById(any())).willReturn(new MemberResponseDto(buyer));

            final List<Trading> tradings = new ArrayList<>();
            tradings.add(new Trading(post, buyer, seller));
            tradings.add(new Trading(post, buyer, seller));

            final Pageable pageable = PageRequest.of(0, tradings.size(), Sort.by(Sort.Direction.DESC, "createdAt"));

            final var response = new PageImpl<>(tradings, pageable, tradings.size());

            given(memberMyPageRepository.findTradingByMemberId(any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = memberMyPageService.findTradingByMemberId(1L, pageable);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalElements()).isEqualTo(response.getTotalElements());
                softAssertions.assertThat(result.getTotalPages()).isEqualTo(response.getTotalPages());
            });
        }

        @DisplayName("[성공T] 자신이 좋아요한 시안 목록 페이징 조회")
        void find_success_my_interests(final Member member, final Post post) throws Exception {
            // given -- 테스트의 상태 설정
            given(memberService.findById(any())).willReturn(new MemberResponseDto(member));

            final List<Interest> interests = new ArrayList<>();
            interests.add(new Interest(member, post));
            interests.add(new Interest(member, post));

            final Pageable pageable = PageRequest.of(0, interests.size(), Sort.by(Sort.Direction.DESC, "createdAt"));

            final var response = new PageImpl<>(interests, pageable, interests.size());

            given(memberMyPageRepository.findInterestByMemberId(any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = memberMyPageService.findInterestByMemberId(1L, pageable);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalElements()).isEqualTo(response.getTotalElements());
                softAssertions.assertThat(result.getTotalPages()).isEqualTo(response.getTotalPages());
            });
        }
    }
}


