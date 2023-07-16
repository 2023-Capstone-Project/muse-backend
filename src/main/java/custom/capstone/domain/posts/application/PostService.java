package custom.capstone.domain.posts.application;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.request.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.request.PostUpdateRequestDto;
import custom.capstone.domain.posts.dto.response.PostResponseDto;
import custom.capstone.domain.posts.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;

    /**
     * 게시글 등록
     */
    @Transactional
    public Long savePost(final PostSaveRequestDto requestDto) {
        final Member member = memberService.findById(requestDto.memberId());
        final Post post = Post.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .price(requestDto.price())
                .member(member)
                .type(requestDto.type())
                .build();

        return postRepository.save(post).getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long updatePost(final Long postId, final PostUpdateRequestDto requestDto) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.update(requestDto.title(), requestDto.content(), requestDto.price(), requestDto.type());


        return postId;
    }

    /**
     * 게시글 페이징 조회
     */
    public Page<Post> findAll(final Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findById(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 게시글 상세 조회
     */
    public PostResponseDto findDetailById(final Long postId) {
        final Post entity = postRepository.findDetailById(postId)
                .orElseThrow(PostNotFoundException::new);

        return new PostResponseDto(entity);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        postRepository.delete(post);
    }
}
