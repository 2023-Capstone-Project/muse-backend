package custom.capstone.domain.inquiry.dao;

import custom.capstone.domain.inquiry.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
