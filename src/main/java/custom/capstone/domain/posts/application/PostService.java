package custom.capstone.domain.posts.application;

import custom.capstone.domain.category.dao.CategoryRepository;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.members.dao.MemberRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PostImageService postImageService;

    /**
     * 게시글 등록
     */
    @Transactional
    public PostSaveResponseDto savePost(
            final String loginEmail,
            final List<MultipartFile> images,
            final PostSaveRequestDto requestDto
    ) throws IOException {

        final Member member = getValidMember(loginEmail);

        final Category category = categoryRepository.findByTitle(requestDto.categoryTitle());

        final Post post = Post.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .price(requestDto.price())
                .member(member)
                .category(category)
                .type(requestDto.type())
                .build();

        postRepository.save(post);
        postImageService.savePostImage(post, images);

        return new PostSaveResponseDto(post.getId());
    }


    /**
     * 게시글 수정
     */
    @Transactional
    public PostResponseDto updatePost(
            final String loginEmail,
            final Long postId,
            final PostUpdateRequestDto requestDto
    ) {
        final Member member = getValidMember(loginEmail);

        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        checkEqualMember(member, post);

        final Category category = categoryRepository.findByTitle(requestDto.categoryTitle());

        post.update(requestDto.title(), requestDto.content(), requestDto.price(), category, requestDto.type(), requestDto.status());

        return new PostResponseDto(post);
    }

    /**
     * 게시글 페이징 조회
     */
    public Page<Post> findAll(final Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Post> findPostsByCategory(final Long categoryId, final Pageable pageable) {
        return postRepository.findPostsByCategory(categoryId, pageable);
    }

    public Post findById(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    /**
     * 게시글 상세 조회
     */
    @Transactional
    public PostResponseDto findDetailById(final Long categoryId, final Long postId) {
        final Post post = postRepository.findDetailById(categoryId, postId)
                .orElseThrow(PostNotFoundException::new);

        post.increaseView();

        return new PostResponseDto(post);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deletePost(final String loginEmail, final Long postId) {
        final Member member = getValidMember(loginEmail);

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

    // 회원 인지 확인
    private Member getValidMember(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 작성한 사용자가 맞는지 확인
    private void checkEqualMember(final Member member, final Post post) {
        if (!post.getMember().getId().equals(member.getId())) {
            throw new PostInvalidAccessException();
        }
    }
}
