package custom.capstone.domain.notice.application;

import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.notice.dao.NoticeRepository;
import custom.capstone.domain.notice.domain.Notice;
import custom.capstone.domain.notice.dto.request.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.request.NoticeUpdateRequestDto;
import custom.capstone.domain.notice.dto.response.NoticeListResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeSaveResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeUpdateResponseDto;
import custom.capstone.domain.notice.exception.NoticeInvalidException;
import custom.capstone.domain.notice.exception.NoticeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeImageService noticeImageService;
    private final MemberRepository memberRepository;

    /**
     * 공지사항 등록
     */
    @Transactional
    public NoticeSaveResponseDto saveNotice(
            final String loginEmail,
            final List<MultipartFile> images,
            final NoticeSaveRequestDto requestDto
    ) throws IOException {
        final Member admin = getValidAdmin(loginEmail);

        final Notice notice = Notice.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .member(admin)
                .build();

        noticeRepository.save(notice);
        noticeImageService.saveNoticeImages(notice, images);

        return new NoticeSaveResponseDto(notice.getId());
    }

    /**
     * 공지사항 수정
     */
    @Transactional
    public NoticeUpdateResponseDto updateNotice(
            final String loginEmail,
            final Long noticeId,
            final NoticeUpdateRequestDto requestDto
    ) {
        final Member admin = getValidAdmin(loginEmail);

        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        checkEqualAdmin(admin, notice);

        notice.update(requestDto.title(), requestDto.content());

        return new NoticeUpdateResponseDto(noticeId);
    }

    /**
     * 공지사항 페이징 조회
     */
    @Transactional
    public Page<NoticeListResponseDto> findAll(final Pageable pageable) {
        final Page<Notice> notices = noticeRepository.findAll(pageable);

        return notices.map(notice -> {
            final String thumbnailUrl = noticeImageService.findThumbnailUrl(notice);

            return new NoticeListResponseDto(notice, thumbnailUrl);
        });
    }

    public Notice findById(final Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);
    }

    /**
     * 공지사항 상세 조회
     */
    @Transactional
    public NoticeResponseDto findDetailById(final Long noticeId) {
        final Notice notice = noticeRepository.findDetailById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        final List<String> imageUrls = noticeImageService.findAllNoticeImages(notice);

        notice.increaseView();

        return new NoticeResponseDto(notice, imageUrls);
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    public void deleteNotice(final String loginEmail, final Long noticeId) {
        final Member admin = getValidAdmin(loginEmail);

        final Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(NoticeNotFoundException::new);

        checkEqualAdmin(admin, notice);

        noticeRepository.delete(notice);
    }

    // 관리자인지 확인
    private Member getValidAdmin(final String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));
    }

    // 작성한 관리자인지 확인
    private void checkEqualAdmin(final Member admin, final Notice notice) {
        if (!notice.getMember().getId().equals(admin.getId()))
            throw new NoticeInvalidException();
    }
}
