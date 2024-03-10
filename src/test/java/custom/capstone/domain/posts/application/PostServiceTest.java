package custom.capstone.domain.posts.application;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import custom.capstone.domain.category.dao.CategoryRepository;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.customization.PostCustomization;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.request.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.response.PostResponseDto;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PostImageService postImageService;

    @Nested
    @DisplayName("게시글 등록")
    class SavePost {

        final List<MultipartFile> images = Arrays.asList(
                mock(MockMultipartFile.class),
                mock(MockMultipartFile.class)
        );

        @DisplayName("[성공T] 로그인한 사용자의 게시글 등록")
        @ParameterizedTest
        @AutoSource
        @Customization(PostCustomization.class)
        void savePost_success(
                final Member member,
                final Category category,
                final PostSaveRequestDto requestDto
        ) throws IOException {
            // given -- 테스트의 상태 설정
            given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
            given(categoryRepository.findByTitle(any())).willReturn(category);

            final Post post = Post.builder()
                    .title(requestDto.title())
                    .content(requestDto.content())
                    .price(requestDto.price())
                    .member(member)
                    .type(requestDto.type())
                    .category(category)
                    .build();

            given(postRepository.save(any())).willReturn(post);

            // when -- 테스트하고자 하는 행동
            final var result = postService.savePost(member.getUsername(), images, requestDto);

            // then -- 예상되는 변화 및 결과
            assertThat(result.postId()).isEqualTo(post.getId());
        }

        @DisplayName("[실패T] 로그인하지 않은 혹은 존재하지 않는 사용자의 게시글 등록 요청 시 실패")
        @ParameterizedTest
        @AutoSource
        void savePost_fail_notFoundMember(final Member member, final PostSaveRequestDto requestDto) {
            // given -- 테스트의 상태 설정
            given(memberRepository.findByEmail(any())).willThrow(UsernameNotFoundException.class);

            // when -- 테스트하고자 하는 행동

            // then -- 예상되는 변화 및 결과
            assertThrows(UsernameNotFoundException.class, () -> postService.savePost(member.getEmail(), images, requestDto));
        }
    }

    @Nested
    @DisplayName("게시글 조회")
    class FindPost {

        @DisplayName("[성공T] 카테고리별 게시글 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void findPosts_by_category_success(final Category category, final List<Post> posts) {
            // given -- 테스트의 상태 설정
            final Pageable pageable = PageRequest.of(0, posts.size(), Sort.by(Sort.Direction.DESC, "createdAt"));

            final var response = new PageImpl<>(posts, pageable, posts.size());

            given(postRepository.findAll(pageable)).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = postService.findAll(pageable);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getTotalElements()).isEqualTo(response.getTotalElements());
                softAssertions.assertThat(result.getTotalPages()).isEqualTo(response.getTotalPages());
            });
        }

        @DisplayName("[성공T] 특정 게시글 상세 조회와 함께 조회수 증가")
        @ParameterizedTest
        @AutoSource
        void findPost_detail_success(final Post post, final List<String> images) {
            // given -- 테스트의 상태 설정
            given(postRepository.findById(any())).willReturn(Optional.of(post));
            given(postImageService.findAllPostImages(any())).willReturn(images);

            final var response = new PostResponseDto(post, images);

            // when -- 테스트하고자 하는 행동
            final var result = postService.findDetailById(1L);

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getViews()).isOne();
                softAssertions.assertThat(result.getTitle()).isEqualTo(response.getTitle());
                softAssertions.assertThat(result.getImageUrls().size()).isEqualTo(response.getImageUrls().size());
            });
        }
    }
}