package custom.capstone.domain.interest.dao;

import custom.capstone.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
