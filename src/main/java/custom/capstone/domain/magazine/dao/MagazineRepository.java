package custom.capstone.domain.magazine.dao;

import custom.capstone.domain.magazine.domain.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MagazineRepository extends JpaRepository<Magazine, Long> {
    @Query(value = "select m" +
            " from Magazine m" +
            " order by m.id desc")
    List<Magazine> findAllDesc();
}
