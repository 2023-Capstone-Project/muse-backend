package custom.capstone.domain.members.dao;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMyPageRepository extends JpaRepository<Member, Long> {
    /**
     * 자신이 작성한 게시글 목록 페이징 조회
     */
    @Query(value = "select p" +
            " from Post p" +
            " join fetch p.member m" +
            " where m.id = :id"
            , countQuery = "select count(p) from Post p")
    Page<Post> findPostsByMemberId(@Param("id") final Long memberId, final Pageable pageable);

    /**
     * 자신이 작성한 후기 목록 페이징 조회
     */
    @Query(value = "select r" +
            " from Review r" +
            " join fetch r.member m" +
            " where m.id = :id"
            , countQuery = "select count(r) from Review r")
    Page<Review> findReviewsByMemberId(@Param("id") final Long memberId, final Pageable pageable);

    // TODO: 수정 필요
    /**
     * 자신이 거래한 시안 목록 페이징 조회
     */
//    @Query(value = "select t" +
//            " from Trading t" +
//            " join fetch t.member m" +
//            " where m.id = :id"
//            , countQuery = "select count(t) from Trading t")
//    Page<Trading> findTradingByMemberId(@Param("id") final Long memberId, final Pageable pageable);

    /**
     * 자신이 좋아요한 시안 목록 페이징 조회
     */
    @Query(value = "select i" +
            " from Interest i" +
            " join fetch i.member m" +
            " join fetch i.post p" +
            " where m.id = :id"
            , countQuery = "select count(i) from Interest i")
    Page<Interest> findInterestByMemberId(@Param("id") final Long memberId, final Pageable pageable);
}
