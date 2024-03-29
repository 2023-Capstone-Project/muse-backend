package custom.capstone.domain.posts.dao;

import custom.capstone.domain.posts.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * 카테고리별 게시글 페이징 조회
     */
    @Query(value = "select p" +
            " from Post p" +
            " where p.category.id = :categoryId")
    Page<Post> findPostsByCategory(final Long categoryId, final Pageable pageable);

    /**
     * 게시글 페이징 조회
     */
    @Query(value = "select p" +
            " from Post p"
            , countQuery = "select count(p) from Post p")
    Page<Post> findAll(final Pageable pageable);

    /**
     * 게시글 상세 조회
     */
    @Query(value = "select p" +
            " from Post p" +
            " join fetch p.category c" +
            " where c.id = :categoryId AND p.id = :postId")
    Optional<Post> findDetailById(@Param("categoryId") final Long categoryId, @Param("postId") final Long postId);

    /**
     * 게시글 통합 검색
     */
    @Query(value = "select p" +
            " from Post p" +
            " where p.title like %:keyword% or p.content like %:keyword%")
    Page<Post> findByKeyword(final String keyword, final Pageable pageable);
}
