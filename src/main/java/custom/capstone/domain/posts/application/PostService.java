package custom.capstone.domain.posts.application;

import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.PostListResponseDto;
import custom.capstone.domain.posts.dto.PostResponseDto;
import custom.capstone.domain.posts.dto.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long savePost(final PostSaveRequestDto requestDto) {
        Post post = Post.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .member(requestDto.author())
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional
    public Long updatePost(final Long postId, final PostUpdateRequestDto requestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));

        post.update(requestDto.title(), requestDto.content(), requestDto.price());

        return postId;
    }

    public Post findById(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NullPointerException::new);
    }

    public PostResponseDto findDetailById(final Long postId) {
        Post entity = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + postId));

        return new PostResponseDto(entity.getId(), entity.getTitle(), entity.getContent(), entity.getMember());
    }

    @Transactional
    public List<PostListResponseDto> findAllDesc() {
        return postRepository.findAllDesc()
                .stream()
                .map(PostListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePost(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id = " + postId));

        postRepository.delete(post);
    }
}
