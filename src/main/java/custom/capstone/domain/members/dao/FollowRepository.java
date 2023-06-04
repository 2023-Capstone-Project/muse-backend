package custom.capstone.domain.members.dao;

import custom.capstone.domain.members.domain.Follow;
import custom.capstone.domain.members.dto.MemberProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    /**
     * 팔로워 조회
     */
    @Query(value = "select m.id, m.nickname" +
            " from Member m" +
            " join Follow f" +
            " on m.id = f.from.id" +
            " where f.to.id = :id")
    Set<MemberProfileDto> findFollowerByToId(@Param("id") final Long toId);

    /**
     * 팔로잉 조회
     */
    @Query("select m.id, m.nickname" +
            " from Member m" +
            " join Follow f" +
            " on m.id = f.to.id" +
            " where f.from.id = :id")
    Set<MemberProfileDto> findFollowingByFromId(@Param("id") final Long fromId);
}
