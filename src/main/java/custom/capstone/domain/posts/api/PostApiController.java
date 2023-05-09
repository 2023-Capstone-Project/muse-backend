package custom.capstone.domain.posts.api;

import custom.capstone.domain.posts.application.PostService;
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
    public Long savePost(@RequestBody PostSaveRequestDto requestDto) {
        return postService.savePost(requestDto);
    };

    @PatchMapping("/{id}/edit")
    public Long updatePost(@PathVariable Long id, @RequestBody PostUpdateRequestDto requestDto){
        return postService.updatePost(id, requestDto);
    }

    @GetMapping("/{id}")
    public PostResponseDto findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    @DeleteMapping("/{id}")
    public Long deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return id;
    }
}
