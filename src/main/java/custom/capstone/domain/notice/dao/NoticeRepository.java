package custom.capstone.domain.notice.dao;

import custom.capstone.domain.notice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value = "select n" +
            " from Notice n" +
            " order by n.id desc")
    List<Notice> findAllDesc();
}
