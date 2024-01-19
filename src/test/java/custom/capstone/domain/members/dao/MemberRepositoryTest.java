package custom.capstone.domain.members.dao;

import autoparams.AutoSource;
import custom.capstone.domain.members.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager em;

    @Nested
    @DisplayName("회원 가입")
    class Join {

        @DisplayName("[성공T] 회원 등록")
        @ParameterizedTest
        @AutoSource
        void saveMember_success(final Member member) {
            // given -- 테스트의 상태 설정

            // when -- 테스트하고자 하는 행동
            final var result = memberRepository.save(member);

            // then -- 예상되는 변화 및 결과
            assertThat(result.getEmail()).isEqualTo(member.getEmail());
        }
    }

    @Nested
    @DisplayName("회원 조회")
    class FindMember {

        @DisplayName("[성공T] 자신의 회원 정보 조회")
        @ParameterizedTest
        @AutoSource
        void findMember_success(final Member member) {
            // given -- 테스트의 상태 설정
            final var expected = memberRepository.save(member);

            // when -- 테스트하고자 하는 행동
            final var result = memberRepository.findById(member.getId()).get();

            // then -- 예상되는 변화 및 결과
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getEmail()).isEqualTo(expected.getEmail());
                softAssertions.assertThat(result.getNickname()).isEqualTo(expected.getNickname());
            });
        }
    }

    @Nested
    @DisplayName("회원 탈퇴")
    class DeleteMember {

        @DisplayName("[성공T] 회원 정보 삭제")
        @ParameterizedTest
        @AutoSource
        void deleteMember_success(final Member member) {
            // given -- 테스트의 상태 설정
            em.persist(member);

            // when -- 테스트하고자 하는 행동
            memberRepository.delete(member);

            // then -- 예상되는 변화 및 결과
            assertThat(memberRepository.findById(member.getId())).isEmpty();
        }
    }
}
