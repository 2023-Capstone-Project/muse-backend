package custom.capstone.domain.notice.application;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.notice.dao.NoticeRepository;
import custom.capstone.domain.notice.domain.Notice;
import custom.capstone.domain.notice.dto.request.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.request.NoticeUpdateRequestDto;
import custom.capstone.domain.notice.dto.response.NoticeListResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeSaveResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeUpdateResponseDto;
import custom.capstone.domain.notice.exception.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

    /**
     * 공지사항 등록
     */
    @Transactional
    public NoticeSaveResponseDto saveNotice(final NoticeSaveRequestDto requestDto) {
        final Member member = memberService.findById(requestDto.memberId());

        final Notice notice = Notice.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .member(member)
                .build();

        noticeRepository.save(notice);

        return new NoticeSaveResponseDto(notice.getId());
    }

    /**
     * 공지사항 수정
     */
    @Transactional
    public NoticeUpdateResponseDto updateNotice(final Long noticeId, final NoticeUpdateRequestDto requestDto) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        notice.update(requestDto.title(), requestDto.content());

        return new NoticeUpdateResponseDto(noticeId);
    }

    /**
     * 공지사항 페이징 조회
     */
    @Transactional
    public Page<NoticeListResponseDto> findAll(final Pageable pageable) {
        return noticeRepository.findAll(pageable)
                .map(NoticeListResponseDto::new);
    }

    public Notice findById(final Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);
    }

    /**
     * 공지사항 상세 조회
     */
    public NoticeResponseDto findDetailById(final Long noticeId) {
        final Notice notice = noticeRepository.findDetailById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        return new NoticeResponseDto(notice);
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    public void deleteNotice(final Long noticeId) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        noticeRepository.delete(notice);
    }
}
