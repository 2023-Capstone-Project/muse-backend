package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.FollowRepository;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Follow;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.dto.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    @Transactional
    public Long save(Long fromId, Long toId) {
        Member from = memberRepository.findById(fromId)
                .orElseThrow(IllegalArgumentException::new);

        Member to = memberRepository.findById(toId)
                .orElseThrow(IllegalArgumentException::new);

        Follow follow = Follow.builder()
                .from(from)
                .to(to)
                .build();

        return followRepository.save(follow).getId();
    }

    /**
     * 팔로잉 끊기
     */
    @Transactional
    public void deleteFollow(Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 팔로우를 찾을 수 없습니다. id = " + id));

        followRepository.delete(follow);
    }

    /**
     * 팔로워, 팔로잉 조회
     */
    @Transactional
    public Set<MemberProfileDto> findFollowers(Long id) {
        return memberRepository.findFollowerByToId(id);
    }

    @Transactional
    public Set<MemberProfileDto> findFollowings(Long id) {
        return memberRepository.findFollowingByFromId(id);
    }
}
