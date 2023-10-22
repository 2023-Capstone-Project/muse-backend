package custom.capstone.domain.review.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.review.dao.ReviewRepository;
import custom.capstone.domain.review.domain.Review;
import custom.capstone.domain.review.dto.request.ReviewSaveRequestDto;
import custom.capstone.domain.review.dto.request.ReviewUpdateRequestDto;
import custom.capstone.domain.review.dto.response.ReviewSaveResponseDto;
import custom.capstone.domain.review.dto.response.ReviewUpdateResponseDto;
import custom.capstone.domain.review.exception.ReviewInvalidException;
import custom.capstone.domain.review.exception.ReviewNotFoundException;
import custom.capstone.domain.trading.dao.TradingRepository;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.exception.TradingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final TradingRepository tradingRepository;

    /**
     * 후기 생성
     */
    @Transactional
    public ReviewSaveResponseDto saveReview(final String loginEmail, final ReviewSaveRequestDto requestDto) {
        final Member member = getValidMember(loginEmail);

        final Trading trading = getValidTrading(member, requestDto.tradingId());
        
        final Review review = Review.builder()
                .trading(trading)
                .member(member)
                .content(requestDto.content())
                .build();

        reviewRepository.save(review);

        return new ReviewSaveResponseDto(review.getId());
    }

    /**
     * 후기 수정
     */
    @Transactional
    public ReviewUpdateResponseDto updateReview(
            final String loginEmail,
            final Long reviewId,
            final ReviewUpdateRequestDto requestDto
    ) {
        final Member member = getValidMember(loginEmail);
        
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        checkEqualMember(member, review);
        
        review.update(requestDto.content());

        return new ReviewUpdateResponseDto(reviewId);
    }

    /**
     * 후기 조회
     */
    public Review findById(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
    }

    /**
     * 후기 삭제
     */
    @Transactional
    public void deleteReview(final String loginEmail, final Long reviewId) {
        final Member member = getValidMember(loginEmail);

        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);

        checkEqualMember(member, review);

        reviewRepository.delete(review);
    }

    // 회원 인지 확인
    private Member getValidMember(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
    
    // 거래자가 맞는지 확인
    private Trading getValidTrading(final Member buyer, final Long tradingId) {
        final Trading trading = tradingRepository.findById(tradingId)
                .orElseThrow(TradingNotFoundException::new);
        
        if (!trading.getBuyer().getId().equals(buyer.getId()))
            throw new ReviewInvalidException();
        
        return trading;
    }
    
    // 후기 작성한 사용자가 맞는지 확인
    private void checkEqualMember(final Member member, final Review review) {
        if (!review.getMember().getId().equals(member.getId()))
            throw new ReviewInvalidException();
    }
}
