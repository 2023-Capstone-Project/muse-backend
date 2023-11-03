package custom.capstone.domain.posts.dao;

import custom.capstone.domain.posts.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    @Query(value = "select pi" +
            " from PostImage pi" +
            " where pi.post.id = :postId")
    PostImage findPostImagesByPostId(final Long postId);
}
