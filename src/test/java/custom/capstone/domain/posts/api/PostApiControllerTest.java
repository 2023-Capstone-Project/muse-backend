package custom.capstone.domain.posts.api;

import autoparams.AutoSource;
import autoparams.customization.Customization;
import com.fasterxml.jackson.databind.ObjectMapper;
import custom.capstone.domain.customization.PostCustomization;
import custom.capstone.domain.posts.application.PostImageService;
import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.domain.PostStatus;
import custom.capstone.domain.posts.domain.PostType;
import custom.capstone.domain.posts.dto.request.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.request.PostUpdateRequestDto;
import custom.capstone.domain.posts.dto.response.PostListResponseDto;
import custom.capstone.domain.posts.dto.response.PostResponseDto;
import custom.capstone.domain.posts.dto.response.PostSaveResponseDto;
import custom.capstone.domain.posts.dto.response.PostUpdateResponseDto;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import custom.capstone.infra.S3.S3Uploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostApiController.class)
public class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private PostImageService postImageService;

    @Spy
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private S3Uploader s3Uploader;

    private final String ACCESS_TOKEN = "accessToken";

    @Nested
    @DisplayName("게시글 등록")
    class SavePost {

        final MockMultipartFile image = new MockMultipartFile(
                "post-image",
                "post-image.jpg",
                IMAGE_JPEG_VALUE,
                "post content image".getBytes(UTF_8)
        );

        @DisplayName("[성공T] 로그인한 사용자의 게시글 등록")
        @ParameterizedTest
        @AutoSource
        @Customization(PostCustomization.class)
        @WithMockUser(username = "muse@muse.com", roles = "GENERAL")
        void savePost_success(final PostSaveRequestDto requestDto) throws Exception {
            // given -- 테스트의 상태 설정
            final var request = new MockMultipartFile(
                    "requestDto",
                    "",
                    APPLICATION_JSON_VALUE,
                    objectMapper.writeValueAsString(requestDto).getBytes(UTF_8)
            );

            final var response = new PostSaveResponseDto(1L);

            given(postService.savePost(any(), any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    multipart("/api/posts")
                            .file(image)
                            .file(request)
                            .contentType(MULTIPART_FORM_DATA_VALUE)
                            .accept(APPLICATION_JSON_VALUE)
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .with(csrf())
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }

        @DisplayName("[실패T] 로그인하지 않은 사용자의 접근으로 인한 게시글 등록 실패")
        @Test
        void savePost_fail_unauthorized() throws Exception {
            // given -- 테스트의 상태 설정
            given(postService.savePost(any(), any(), any())).willThrow(UsernameNotFoundException.class);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    multipart("/api/posts")
                            .contentType(MULTIPART_FORM_DATA_VALUE)
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("게시글 수정")
    class UpdatePost {

        @DisplayName("[성공T] 로그인한 사용자가 자신이 작성한 게시글을 수정 & 수정된 내용으로 최신화")
        @Test
        @WithMockUser(username = "muse@muse.com", roles = "GENERAL")
        void updatePost_success() throws Exception {
            // given -- 테스트의 상태 설정
            final String loginEmail = "muse@muse.com";

            final var request = new PostUpdateRequestDto(
                    "re-title",
                    "re-content",
                    20000,
                    "악세사리",
                    PostType.REFORM,
                    PostStatus.SALE
            );

            final var response = new PostUpdateResponseDto(1L);

            given(postService.updatePost(any(), any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    patch("/api/posts/{postId}", 1L)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .header("Authorization", "Bearer " + ACCESS_TOKEN)
                            .with(user(loginEmail).roles("GENERAL"))
                            .with(csrf())
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("게시글 조회")
    class FindPost {

        @DisplayName("[성공T] 카테고리별 게시글 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void findPosts_by_category_success(final List<Post> posts) throws Exception{
            // given -- 테스트의 상태 설정
            final Pageable pageable = PageRequest.of(0, posts.size(), Sort.by(DESC, "createdAt"));

            final var response = new PageImpl<>(posts.stream()
                            .map(post -> new PostListResponseDto(post, "thumbnailUrl"))
                            .collect(Collectors.toList()), pageable, posts.size()
            );

            given(postService.findPostsByCategory(any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    get("/api/posts/{categoryId}", 1L)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }

        @DisplayName("[성공T] 특정 게시글 상세 조회")
        @ParameterizedTest
        @AutoSource
        void findPost_detail_success(final Post post, final List<String> imageUrls) throws Exception {
            // given -- 테스트의 상태 설정
            given(postImageService.findAllPostImages(any())).willReturn(imageUrls);

            final var response = new PostResponseDto(post, imageUrls);

            given(postService.findDetailById(any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    get("/api/posts/{categoryId}/{postId}", 1L, 1L)
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("게시글 검색")
    class SearchPost {

        @DisplayName("[성공T] 검색하고자 하는 키워드와 연관된 게시글 통합 검색")
        @ParameterizedTest
        @AutoSource
        void searchPosts_success(final List<Post> posts) throws Exception{
            // given -- 테스트의 상태 설정
            final Pageable pageable = PageRequest.of(0, posts.size(), Sort.by(DESC, "createdAt"));

            final var response = new PageImpl<>(posts.stream()
                    .map(post -> new PostListResponseDto(post, "thumbnailUrl"))
                    .collect(Collectors.toList()), pageable, posts.size()
            );

            given(postService.searchPosts(any(), any())).willReturn(response);

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    get("/api/posts/search")
                            .contentType(APPLICATION_JSON)
                            .accept(APPLICATION_JSON)
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("게시글 삭제")
    class DeletePost {

        @DisplayName("[성공T] 자신이 작성한 게시글 삭제")
        @Test
        @WithMockUser(username = "muse@muse.com", roles = "GENERAL")
        void deletePost_success() throws Exception {
            // given -- 테스트의 상태 설정
            final String loginEmail = "muse@muse.com";

            // when -- 테스트하고자 하는 행동
            final var result = mockMvc.perform(
                    delete("/api/posts/{postId}", 1L)
                            .with(user(loginEmail).roles("GENERAL"))
                            .with(csrf())
            );

            // then -- 예상되는 변화 및 결과
            result.andExpect(status().isOk());
        }
    }
}
