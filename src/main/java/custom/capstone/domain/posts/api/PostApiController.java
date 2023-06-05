package custom.capstone.domain.posts.api;

import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.PostResponseDto;
import custom.capstone.domain.posts.dto.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
    private final PostService postService;

    @PostMapping("/write")
    public Long savePost(@RequestBody final PostSaveRequestDto requestDto) {
        return postService.savePost(requestDto);
    };

    @PatchMapping("/{postId}/edit")
    public Long updatePost(@PathVariable("postId") final Long id,
                           @RequestBody PostUpdateRequestDto requestDto){
        return postService.updatePost(id, requestDto);
    }

    @GetMapping("/{postId}")
    public Post findById(@PathVariable("postId") final Long id) {
        return postService.findById(id);
    }

    @GetMapping("/{postId}")
    public PostResponseDto findPostById(@PathVariable("postId") final Long id) {
        return postService.findDetailById(id);
    }

    @DeleteMapping("/{postId}")
    public Long deletePost(@PathVariable("postId") final Long id) {
        postService.deletePost(id);
        return id;
    }
}
