package custom.capstone.domain.members.dao;

import custom.capstone.domain.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(final String email);

    Optional<Member> findMemberByNickname(final String nickname);
}