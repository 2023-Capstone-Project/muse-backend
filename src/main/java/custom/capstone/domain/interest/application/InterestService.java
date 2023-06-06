package custom.capstone.domain.interest.application;

import custom.capstone.domain.interest.dao.InterestRepository;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.interest.dto.InterestSaveRequestDto;
import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;
    private final MemberService memberService;
    private final PostService postService;

    /**
     * 좋아요 생성
     */
    @Transactional
    public Long saveInterest(final InterestSaveRequestDto requestDto) {
        Member member = memberService.findById(requestDto.memberId());
        Post post = postService.findById(requestDto.postId());

        return interestRepository.save(new Interest(member, post)).getId();
    }

    /**
     * 좋아요 취소
     */
    @Transactional
    public void cancelInterest(final Long interestId) {
        interestRepository.delete(findById(interestId));
    }

    /**
     * 좋아요 조회
     */
    public Interest findById(final Long interestId) {
        return interestRepository.findById(interestId)
                .orElseThrow(NullPointerException::new);
    }
}
