package custom.capstone.domain.trading.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.exception.PostNotFoundException;
import custom.capstone.domain.trading.dao.TradingRepository;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.dto.request.TradingSaveRequestDto;
import custom.capstone.domain.trading.dto.request.TradingUpdateRequestDto;
import custom.capstone.domain.trading.dto.response.TradingResponseDto;
import custom.capstone.domain.trading.dto.response.TradingSaveResponseDto;
import custom.capstone.domain.trading.exception.TradingInvalidException;
import custom.capstone.domain.trading.exception.TradingNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradingService {
    private final TradingRepository tradingRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /**
     * 거래 생성
     */
    @Transactional
    public TradingSaveResponseDto saveTrading(final String loginEmail, final TradingSaveRequestDto requestDto) {
        final Member buyer = getValidMember(loginEmail);

        final Post post = getValidPost(requestDto.postId());

        final Member seller = post.getMember();

        // 판매자(게시글 작성자)와 구매자가 같을 시 예외처리
        if (seller.getId().equals(buyer.getId())) {
            throw new TradingInvalidException();
        }

        final Trading trading = Trading.builder()
                .post(post)
                .buyer(buyer)
                .seller(seller)
                .build();

        tradingRepository.save(trading);

        return new TradingSaveResponseDto(trading.getId());
    }

    /**
     * 거래 수정
     */
    @Transactional
    public TradingResponseDto updateTrading(
            final String loginEmail,
            final Long tradingId,
            final TradingUpdateRequestDto requestDto
    ) {
        final Member seller = getValidMember(loginEmail);

        final Trading trading = tradingRepository.findById(tradingId)
                .orElseThrow(TradingNotFoundException::new);

        // 판매자가 아닐 경우 예외처리
        if (!trading.getSeller().equals(seller))
            throw new TradingInvalidException();

        trading.update(requestDto.status());

        return new TradingResponseDto(seller.getNickname(), requestDto.status());
    }

    /**
     * 거래 조회
     */
    public Trading findById(final Long tradingId) {
        return tradingRepository.findById(tradingId)
                .orElseThrow(TradingNotFoundException::new);
    }

    // 회원 인지 확인
    private Member getValidMember(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    // 거래되는 디자인 시안 게시글 확인
    private Post getValidPost(final Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
