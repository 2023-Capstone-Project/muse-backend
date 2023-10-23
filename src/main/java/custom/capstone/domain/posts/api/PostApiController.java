package custom.capstone.domain.posts.api;

import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.dto.request.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.request.PostUpdateRequestDto;
import custom.capstone.domain.posts.dto.response.PostListResponseDto;
import custom.capstone.domain.posts.dto.response.PostResponseDto;
import custom.capstone.domain.posts.dto.response.PostSaveResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping
    public BaseResponse<PostSaveResponseDto> savePost(
            @AuthenticationPrincipal final String loginEmail,
            @Valid @RequestBody final PostSaveRequestDto requestDto
    ) {
        final PostSaveResponseDto result = postService.savePost(loginEmail, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.POST_SAVE_SUCCESS,
                result
        );
    };

    @Operation(summary = "게시글 수정")
    @PatchMapping("/{postId}")
    public BaseResponse<PostResponseDto> updatePost(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("postId") final Long id,
            @Valid @RequestBody final PostUpdateRequestDto requestDto
    ) {
        final PostResponseDto result = postService.updatePost(loginEmail, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.POST_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "카테고리별 페이징 조회")
    @GetMapping("/{categoryId}")
    public Page<PostListResponseDto> getCategoryPost(@PathVariable("categoryId") final Long id, final Pageable pageable) {
        return postService.findPostsByCategory(id, pageable)
                .map(PostListResponseDto::new);
    }

    @Operation(summary = "게시글 페이징 조회")
    @GetMapping
    public Page<PostListResponseDto> findAll(final Pageable pageable) {
        return postService.findAll(pageable)
                .map(PostListResponseDto::new);
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{categoryId}/{postId}")
    public BaseResponse<PostResponseDto> getPostDetail(
            @PathVariable("categoryId") final Long categoryId,
            @PathVariable("postId") final Long postId
    ) {
        final PostResponseDto result = postService.findDetailById(categoryId, postId);

        return BaseResponse.of(
                BaseResponseStatus.POST_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public Long deletePost(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("postId") final Long id
    ) {
        postService.deletePost(loginEmail, id);
        return id;
    }

    @Operation(summary = "키워드 검색")
    @GetMapping("/search")
    public Page<PostListResponseDto> searchPosts(
            @RequestParam(value = "keyword", required = false) final String keyword,
            @PageableDefault(direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        return postService.searchPosts(keyword, pageable)
                .map(PostListResponseDto::new);
    }
}
