package custom.capstone.domain.members.dao;

import custom.capstone.domain.members.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
