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
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @Operation(summary = "게시글 등록")
    @PostMapping
    public BaseResponse<PostSaveResponseDto> savePost(@RequestBody final PostSaveRequestDto requestDto) {
        final PostSaveResponseDto result = postService.savePost(requestDto);

        return BaseResponse.of(
                BaseResponseStatus.POST_SAVE_SUCCESS,
                result
        );
    };

    @Operation(summary = "게시글 수정")
    @PatchMapping("/{postId}")
    public BaseResponse<PostResponseDto> updatePost(@PathVariable("postId") final Long id,
                           @RequestBody final PostUpdateRequestDto requestDto) {
        final PostResponseDto result = postService.updatePost(id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.POST_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "게시글 페이징 조회")
    @GetMapping
    public Page<PostListResponseDto> findAll(final Pageable pageable) {
        return postService.findAll(pageable)
                .map(PostListResponseDto::new);
    }

    @Operation(summary = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public BaseResponse<PostResponseDto> findDetailById(@PathVariable("postId") final Long id) {
        final PostResponseDto result = postService.findDetailById(id);

        return BaseResponse.of(
                BaseResponseStatus.POST_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public Long deletePost(@PathVariable("postId") final Long id) {
        postService.deletePost(id);
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
