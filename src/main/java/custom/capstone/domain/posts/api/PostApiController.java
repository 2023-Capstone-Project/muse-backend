package custom.capstone.domain.posts.api;

import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.PostResponseDto;
import custom.capstone.domain.posts.dto.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.PostUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 API")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @Operation(summary = "게시글 생성")
    @PostMapping("/write")
    public Long savePost(@RequestBody final PostSaveRequestDto requestDto) {
        return postService.savePost(requestDto);
    };

    @Operation(summary = "게시글 수정")
    @PatchMapping("/{postId}/edit")
    public Long updatePost(@PathVariable("postId") final Long id,
                           @RequestBody PostUpdateRequestDto requestDto){
        return postService.updatePost(id, requestDto);
    }

    @Operation(summary = "게시글 조회")
    @GetMapping("/{postId}")
    public PostResponseDto findPostById(@PathVariable("postId") final Long id) {
        return postService.findDetailById(id);
    }

    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public Long deletePost(@PathVariable("postId") final Long id) {
        postService.deletePost(id);
        return id;
    }
}
