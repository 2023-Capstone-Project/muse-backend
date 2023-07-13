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
            " where p.id = :id")
    Optional<Post> findDetailById(@Param("id") final Long postId);
}
