package custom.capstone.domain.notice.application;

import custom.capstone.domain.notice.dao.NoticeRepository;
import custom.capstone.domain.notice.domain.Notice;
import custom.capstone.domain.notice.dto.response.NoticeListResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeResponseDto;
import custom.capstone.domain.notice.dto.request.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.request.NoticeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    @Transactional
    public Long saveNotice(NoticeSaveRequestDto requestDto) {
        Notice notice = Notice.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .views(requestDto.views())
                .build();

        return noticeRepository.save(notice).getId();
    }

    @Transactional
    public Long updateNotice(Long id, NoticeUpdateRequestDto requestDto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id = " + id));

        notice.update(requestDto.title(), requestDto.content());

        return id;
    }

    public NoticeResponseDto findNoticeById(Long id) {
        Notice entity = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id = " + id));

        return new NoticeResponseDto(entity.getId(), entity.getTitle(), entity.getContent(), entity.getViews());
    }

    @Transactional
    public List<NoticeListResponseDto> findAllDesc() {
        return noticeRepository.findAllDesc()
                .stream()
                .map(entity -> new NoticeListResponseDto(entity.getId(), entity.getTitle(), entity.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id = " + id));

        noticeRepository.delete(notice);
    }
}
