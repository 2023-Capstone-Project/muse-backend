package custom.capstone.domain.members.application;

import custom.capstone.domain.members.dao.FollowRepository;
import custom.capstone.domain.members.domain.Follow;
import custom.capstone.domain.members.dto.MemberProfileDto;
import custom.capstone.domain.members.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .orElseThrow(MemberNotFoundException::new);

        followRepository.delete(follow);
    }
}
