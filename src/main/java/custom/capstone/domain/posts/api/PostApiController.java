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
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<PostSaveResponseDto> savePost(
            @AuthenticationPrincipal final String loginEmail,
            @RequestPart(value = "imageUrls", required = false) final List<MultipartFile> images,
            @Valid @RequestPart("requestDto") final PostSaveRequestDto requestDto
    ) throws IOException {
        final PostSaveResponseDto result = postService.savePost(loginEmail, images, requestDto);

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
    public BaseResponse<Page<PostListResponseDto>> getCategoryPost(
            @PathVariable("categoryId") final Long id,
            @PageableDefault(size = 20) final Pageable pageable
    ) {
        final Page<PostListResponseDto> result = postService.findPostsByCategory(id, pageable);

        return BaseResponse.of(
                BaseResponseStatus.POST_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "게시글 페이징 조회")
    @GetMapping
    public BaseResponse<Page<PostListResponseDto>> findAll(@PageableDefault(size = 20) final Pageable pageable) {
        final Page<PostListResponseDto> result = postService.findAll(pageable);

        return BaseResponse.of(
                BaseResponseStatus.POST_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{categoryId}/{postId}")
    public BaseResponse<PostResponseDto> getPostDetail(
            @PathVariable("categoryId") final Long categoryId,
            @PathVariable("postId") final Long postId
    ) {
        final PostResponseDto result = postService.findDetailById(categoryId, postId);

        return BaseResponse.of(
                BaseResponseStatus.POST_DETAIL_READ_SUCCESS,
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
    public BaseResponse<Page<PostListResponseDto>> searchPosts(
            @RequestParam(value = "keyword", required = false) final String keyword,
            @PageableDefault(size = 20, direction = Sort.Direction.DESC) final Pageable pageable
    ) {
        final Page<PostListResponseDto> result = postService.searchPosts(keyword, pageable);

        return BaseResponse.of(
                BaseResponseStatus.POST_SEARCH_SUCCESS,
                result
        );
    }
}
