package custom.capstone.domain.interest.application;

import custom.capstone.domain.interest.dao.InterestRepository;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.interest.dto.request.InterestDeleteRequestDto;
import custom.capstone.domain.interest.dto.request.InterestSaveRequestDto;
import custom.capstone.domain.interest.exception.InterestNotFoundException;
import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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
        final Member member = memberService.findById(requestDto.memberId());
        final Post post = postService.findById(requestDto.postId());

        return interestRepository.save(Interest.save(member, post)).getId();
    }

    /**
     * 좋아요 취소
     */
    @Transactional
    public void cancelInterest(final InterestDeleteRequestDto requestDto) {
        final Member member = memberService.findById(requestDto.memberId());
        final Post post = postService.findById(requestDto.postId());

        final Interest interest = interestRepository.findByMemberAndPost(member, post);

        interestRepository.delete(interest);
    }

    /**
     * 좋아요 조회
     */
    public Interest findById(final Long interestId) {
        return interestRepository.findById(interestId)
                .orElseThrow(InterestNotFoundException::new);
    }
}
