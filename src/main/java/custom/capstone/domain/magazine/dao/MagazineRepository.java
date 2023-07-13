package custom.capstone.domain.magazine.dao;

import custom.capstone.domain.magazine.domain.Magazine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    /**
     * 매거진 페이징 조회
     */
    @Query(value = "select mz" +
            " from Magazine mz"
            , countQuery = "select count(m) from Magazine m")
    Page<Magazine> findAll(final Pageable pageable);

    /**
     * 매거진 상세 조회
     */
    @Query(value = "select mz" +
            " from Magazine mz" +
            " where mz.id = :id")
    Optional<Magazine> findDetailById(@Param("id") final Long magazineId);
}
