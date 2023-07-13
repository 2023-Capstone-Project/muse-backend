package custom.capstone.domain.notice.application;

import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.notice.dao.NoticeRepository;
import custom.capstone.domain.notice.domain.Notice;
import custom.capstone.domain.notice.dto.response.NoticeListResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeResponseDto;
import custom.capstone.domain.notice.dto.request.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.request.NoticeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

    /**
     * 공지사항 등록
     */
    @Transactional
    public Long saveNotice(final NoticeSaveRequestDto requestDto) {
        final Member member = memberService.findById(requestDto.memberId());
        final Notice notice = Notice.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .member(member)
                .build();

        return noticeRepository.save(notice).getId();
    }

    /**
     * 공지사항 수정
     */
    @Transactional
    public Long updateNotice(final Long noticeId, final NoticeUpdateRequestDto requestDto) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id = " + noticeId));

        notice.update(requestDto.title(), requestDto.content());

        return noticeId;
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
                .orElseThrow(() -> new NotFoundException("해당 공지사항이 없습니다. id = " + noticeId));
    }

    /**
     * 공지사항 상세 조회
     */
    public NoticeResponseDto findDetailById(final Long noticeId) {
        final Notice entity = noticeRepository.findDetailById(noticeId)
                .orElseThrow(() -> new NotFoundException("해당 공지사항이 없습니다. id = " + noticeId));

        return new NoticeResponseDto(entity);
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    public void deleteNotice(final Long noticeId) {
        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id = " + noticeId));

        noticeRepository.delete(notice);
    }
}
