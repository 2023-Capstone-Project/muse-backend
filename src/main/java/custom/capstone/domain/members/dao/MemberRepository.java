package custom.capstone.domain.members.dao;

import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.dto.MemberProfileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByNickname(String nickname);

    /**
     * 팔로워 조회
     */
    @Query("select new custom.capstone.domain.members.dto.MemberProfileDto(m.id, m.nickname)" +
            " from Member m" +
            " join Follow f" +
            " on m.id = f.from.id" +
            " where f.to.id = :id")
    Set<MemberProfileDto> findFollowerByToId(@Param("id") Long id);

    /**
     * 팔로잉 조회
     */
    @Query("select new custom.capstone.domain.members.dto.MemberProfileDto(m.id, m.nickname)" +
            " from Member m" +
            " join Follow f" +
            " on m.id = f.to.id" +
            " where f.from.id = :id")
    Set<MemberProfileDto> findFollowingByFromId(@Param("id") Long id);
}