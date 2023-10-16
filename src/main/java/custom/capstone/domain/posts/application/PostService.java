package custom.capstone.domain.posts.application;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.dto.request.PostSaveRequestDto;
import custom.capstone.domain.posts.dto.request.PostUpdateRequestDto;
import custom.capstone.domain.posts.dto.response.PostResponseDto;
import custom.capstone.domain.posts.dto.response.PostSaveResponseDto;
import custom.capstone.domain.posts.exception.PostInvalidAccessException;
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

    /**
     * 게시글 등록
     */
    @Transactional
    public PostSaveResponseDto savePost(final Member member, final PostSaveRequestDto requestDto) {

        final Post post = Post.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .price(requestDto.price())
                .member(member)
                .type(requestDto.type())
                .build();

        postRepository.save(post);

        return new PostSaveResponseDto(post.getId());
    }


    /**
     * 게시글 수정
     */
    @Transactional
    public PostResponseDto updatePost(final Member member,
                                      final Long postId,
                                      final PostUpdateRequestDto requestDto
    ) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        checkEqualMember(member, post);

        post.update(requestDto.title(), requestDto.content(), requestDto.price(), requestDto.type(), requestDto.status());

        return new PostResponseDto(post);
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
        final Post post = postRepository.findDetailById(postId)
                .orElseThrow(PostNotFoundException::new);

        post.increaseView();
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(final Member member, final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        checkEqualMember(member, post);

        postRepository.delete(post);
    }

    /**
     * 게시글 통합 검색
     */
    @Transactional
    public Page<Post> searchPosts(final String keyword, final Pageable pageable) {
        return postRepository.findByKeyword(keyword, pageable);
    }

    /**
     * 작성자 확인
     */
    private void checkEqualMember(final Member member, final Post post) {
        if (!post.getMember().getId().equals(member.getId())) {
            throw new PostInvalidAccessException();
        }
    }
}
