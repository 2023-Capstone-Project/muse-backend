package custom.capstone.domain.notice.dao;

import custom.capstone.domain.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    /**
     * 공지사항 페이징 조회
     */
    @Query(value = "select n" +
            " from Notice n"
            , countQuery = "select count(n) from Notice n")
    Page<Notice> findAll(final Pageable pageable);

    /**
     * 공지사항 상세 조회
     */
    @Query(value = "select n" +
            " from Notice n" +
            " where n.id = :id")
    Optional<Notice> findDetailById(@Param("id") final Long noticeId);
}
