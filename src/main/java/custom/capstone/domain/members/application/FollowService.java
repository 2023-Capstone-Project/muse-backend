package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.FollowRepository;
import custom.capstone.domain.members.domain.Follow;
import custom.capstone.domain.members.dto.MemberProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    /**
     * 팔로워 조회
     */
    @Transactional
    public Set<MemberProfileDto> findFollowers(final Long toId) {
        return followRepository.findFollowerByToId(toId);
    }

    /**
     * 팔로잉 조회
     */
    @Transactional
    public Set<MemberProfileDto> findFollowings(final Long fromId) {
        return followRepository.findFollowingByFromId(fromId);
    }

    /**
     * 팔로잉 끊기
     */
    @Transactional
    public void deleteFollow(final Long id) {
        Follow follow = followRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 팔로우를 찾을 수 없습니다. id = " + id));

        followRepository.delete(follow);
    }
}
