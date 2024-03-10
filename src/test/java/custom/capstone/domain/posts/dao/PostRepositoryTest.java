package custom.capstone.domain.posts.dao;

import autoparams.AutoSource;
import custom.capstone.domain.category.domain.Category;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static custom.capstone.domain.posts.domain.PostType.CUSTOM;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager em;

    @Nested
    @DisplayName("게시글 등록")
    class SavePost {

        @DisplayName("[성공T] 게시글 등록")
        @ParameterizedTest
        @AutoSource
        void savePost_success(
                final Member member,
                final Category category,
                final Post post
        ) {
            // given -- 테스트의 상태 설정
            em.persist(member);
            em.persist(category);

            // when -- 테스트하고자 하는 행동
            final var result = postRepository.save(post);

            // then -- 예상되는 변화 및 결과
            assertThat(result.getTitle()).isEqualTo(post.getTitle());
        }
    }

    @Nested
    @DisplayName("게시글 조회")
    class FindPost {

        @DisplayName("[성공T] 카테고리별 게시글 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void findPost_by_category_success(final Category category, final List<Post> posts) {
            // given -- 테스트의 상태 설정
            em.persist(category);

            postRepository.saveAll(posts);

            final Pageable pageable = PageRequest.of(
                    0,
                    posts.size(),
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );

            // when -- 테스트하고자 하는 행동
            final var result = postRepository.findPostsByCategory(category.getId(), pageable);

            // then -- 예상되는 변화 및 결과
            assertThat(result.getSize()).isEqualTo(posts.size());
        }

        @DisplayName("[성공T] 전체 게시글 페이징 조회")
        @ParameterizedTest
        @AutoSource
        void findPost_all_success(final List<Post> posts) {
            // given -- 테스트의 상태 설정
            posts.forEach(em::persist);

            final Pageable pageable = PageRequest.of(
                    0,
                    posts.size(),
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );

            // when -- 테스트하고자 하는 행동
            final var result = postRepository.findAll(pageable);

            // then -- 예상되는 변화 및 결과
            assertThat(result.getSize()).isEqualTo(posts.size());
        }
    }

    @Nested
    @DisplayName("게시글 검색")
    class SearchPost {

        @DisplayName("[성공T] 입력한 키워드와 관련 있는 게시글 검색")
        @ParameterizedTest
        @AutoSource
        void searchPost_by_keyword(
                final Member member,
                final Category category,
                final List<Post> posts
        ) {
            // given -- 테스트의 상태 설정
            em.persist(member);
            em.persist(category);
            posts.forEach(em::persist);

            final String keyword = "keyword";

            final Post post = Post.builder()
                    .title("title")
                    .content("test" + keyword)
                    .price(10000)
                    .member(member)
                    .category(category)
                    .type(CUSTOM)
                    .build();

            postRepository.save(post);

            final Pageable pageable = PageRequest.of(
                    0,
                    posts.size(),
                    Sort.by(Sort.Direction.DESC, "createdAt")
            );

            // when -- 테스트하고자 하는 행동
            final var result = postRepository.findByKeyword(keyword, pageable);

            // then -- 예상되는 변화 및 결과
            assertThat(result.getContent().size()).isOne();
        }
    }
}
