package custom.capstone.domain.inquiry.dao;

import custom.capstone.domain.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
