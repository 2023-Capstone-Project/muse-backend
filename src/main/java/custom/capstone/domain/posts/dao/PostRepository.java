package custom.capstone.domain.posts.dao;

import custom.capstone.domain.posts.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p" +
            " from Post p" +
            " order by p.id desc ")
    List<Post> findAllDesc();
}
