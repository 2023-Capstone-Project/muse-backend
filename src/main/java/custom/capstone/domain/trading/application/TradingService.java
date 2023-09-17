package custom.capstone.domain.trading.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.exception.MemberNotFoundException;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.exception.PostNotFoundException;
import custom.capstone.domain.trading.dao.TradingRepository;
import custom.capstone.domain.trading.domain.Trading;
import custom.capstone.domain.trading.dto.request.TradingSaveRequestDto;
import custom.capstone.domain.trading.dto.request.TradingUpdateRequestDto;
import custom.capstone.domain.trading.dto.response.TradingResponseDto;
import custom.capstone.domain.trading.exception.TradingNotFoundException;
import custom.capstone.global.exception.InvalidAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

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
    public Long saveTrading(final TradingSaveRequestDto requestDto) {
        final Post post = postRepository.findById(requestDto.postId())
                .orElseThrow(PostNotFoundException::new);

        final Member buyer = memberRepository.findById(requestDto.buyerId())
                .orElseThrow(() -> new NotFoundException("구매자를 찾을 수 없습니다."));

        final Member seller = memberRepository.findById(requestDto.sellerId())
                .orElseThrow(() -> new NotFoundException("판매자를 찾을 수 없습니다."));

        // 게시글 작성자 = 판매자 즉, 판매자가 아닐 시 예외 발생
        if (!post.getMember().getId().equals(seller.getId())) {
            throw new InvalidAccessException();
        }

        final Trading trading = Trading.builder()
                .post(post)
                .buyer(buyer)
                .seller(seller)
                .build();

        tradingRepository.save(trading);

        return trading.getId();
    }

    /**
     * 거래 수정
     */
    @Transactional
    public TradingResponseDto updateTrading(final Long tradingId, final TradingUpdateRequestDto requestDto) {
        final Trading trading = tradingRepository.findById(tradingId)
                .orElseThrow(TradingNotFoundException::new);

        final Post post = postRepository.findById(requestDto.postId())
                .orElseThrow(PostNotFoundException::new);

        final Member seller = getValidMember();

        // 게시글 작성자 = 판매자 즉, 판매자가 아닐 시 예외 발생
        if (!post.getMember().getId().equals(seller.getId())) {
            throw new InvalidAccessException();
        }

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

    /**
     * 거래 삭제
     */
    @Transactional
    public void deleteTrading(final Long tradingId) {
        final Trading trading = tradingRepository.findById(tradingId)
                .orElseThrow(TradingNotFoundException::new);

        getValidMember();

        tradingRepository.delete(trading);
    }

    /**
     * 판매자 확인
     */
    private Member getValidMember() {
        final String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }
}
