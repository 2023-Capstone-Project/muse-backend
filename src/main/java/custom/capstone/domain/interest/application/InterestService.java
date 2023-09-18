package custom.capstone.domain.interest.application;

import custom.capstone.domain.interest.dao.InterestRepository;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.interest.dto.request.InterestDeleteRequestDto;
import custom.capstone.domain.interest.dto.request.InterestSaveRequestDto;
import custom.capstone.domain.interest.dto.response.InterestSaveResponseDto;
import custom.capstone.domain.interest.exception.InterestDuplicateException;
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
    public InterestSaveResponseDto saveInterest(final InterestSaveRequestDto requestDto) {
        final Member member = memberService.findById(requestDto.memberId());

        final Post post = postService.findById(requestDto.postId());

        if (interestRepository.existsByMemberAndPost(member, post)) {
            throw new InterestDuplicateException();
        }

        final Interest interest = interestRepository.save(Interest.save(member, post));
        interest.getPost().increaseInterestCount();

        return new InterestSaveResponseDto(interest.getId());
    }

    /**
     * 좋아요 취소
     */
    @Transactional
    public void cancelInterest(final InterestDeleteRequestDto requestDto) {
        final Interest interest = interestRepository.findById(requestDto.interestId())
                .orElseThrow(InterestNotFoundException::new);

        interest.getPost().decreaseInterestCount();
        interestRepository.delete(interest);
    }
}
