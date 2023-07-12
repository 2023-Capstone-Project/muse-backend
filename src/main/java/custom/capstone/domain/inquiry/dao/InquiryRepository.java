package custom.capstone.domain.inquiry.dao;

import custom.capstone.domain.inquiry.domain.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
//    @Query(value = "select i" +
//            " from Inquiry  i" +
//            " join fetch i.answer" +
//            " where i.id = :inqueryId")
//    Optional<Inquiry> findByIdWithAnswer(@Param("inquiryId") final Long id);
}
