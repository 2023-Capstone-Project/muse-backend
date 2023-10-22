package custom.capstone.domain.interest.application;

import custom.capstone.domain.interest.dao.InterestRepository;
import custom.capstone.domain.interest.domain.Interest;
import custom.capstone.domain.interest.dto.request.InterestDeleteRequestDto;
import custom.capstone.domain.interest.dto.request.InterestSaveRequestDto;
import custom.capstone.domain.interest.dto.response.InterestSaveResponseDto;
import custom.capstone.domain.interest.exception.InterestDuplicateException;
import custom.capstone.domain.interest.exception.InterestInvalidException;
import custom.capstone.domain.interest.exception.InterestNotFoundException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.application.PostService;
import custom.capstone.domain.posts.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {
    private final InterestRepository interestRepository;
    private final MemberRepository memberRepository;
    private final PostService postService;

    /**
     * 좋아요 생성
     */
    @Transactional
    public InterestSaveResponseDto saveInterest(final String loginEmail, final InterestSaveRequestDto requestDto) {

        final Member member = getValidMember(loginEmail);

        final Post post = postService.findById(requestDto.postId());

        if (interestRepository.existsByMemberAndPost(member, post)) {
            throw new InterestDuplicateException();
        }

        final Interest interest = new Interest(member, post);

        interestRepository.save(interest);

        post.increaseInterestCount();

        return new InterestSaveResponseDto(interest.getId());
    }

    /**
     * 좋아요 취소
     */
    @Transactional
    public void cancelInterest(final String loginEmail, final InterestDeleteRequestDto requestDto) {
        final Member member = getValidMember(loginEmail);

        final Interest interest = interestRepository.findById(requestDto.interestId())
                .orElseThrow(InterestNotFoundException::new);

        checkEqualMember(member, interest);

        interest.getPost().decreaseInterestCount();
        interestRepository.delete(interest);
    }

    // 회원인지 확인
    private Member getValidMember(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 좋아요 한 사람이 맞는지 확인
    private void checkEqualMember(final Member member, final Interest interest) {
        if (!interest.getMember().getId().equals(member.getId()))
            throw new InterestInvalidException();
    }
}
