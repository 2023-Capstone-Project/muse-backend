package custom.capstone.domain.interest.dao;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    //    @Query(value = "select i" +
//            " from Interest i" +
//            " join fetch Member m" +
//            " join fetch Post p" +
//            " where i.member.id = m.id and i.post.id = p.id")
    Interest findByMemberAndPost(final Member member, final Post post);
}
