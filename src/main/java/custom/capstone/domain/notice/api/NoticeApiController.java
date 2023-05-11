package custom.capstone.domain.notice.api;

import custom.capstone.domain.notice.application.NoticeService;
import custom.capstone.domain.notice.dto.NoticeResponseDto;
import custom.capstone.domain.notice.dto.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.NoticeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeApiController {
    private final NoticeService noticeService;

    @PostMapping("/write")
    public Long saveNotice(@RequestBody NoticeSaveRequestDto requestDto) {
        return noticeService.saveNotice(requestDto);
    }

    @PatchMapping("/{id}/edit")
    public Long updateNotice(@PathVariable Long id, @RequestBody NoticeUpdateRequestDto requestDto) {
        return noticeService.updateNotice(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteNotice(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return id;
    }

    @GetMapping("/{id}")
    public NoticeResponseDto findNoticeById(@PathVariable Long id) {
        return noticeService.findNoticeById(id);
    }
}
