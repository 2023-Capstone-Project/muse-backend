package custom.capstone.domain.interest.dao;

import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
     boolean existsByMemberAndPost(final Member member, final Post post);
}
