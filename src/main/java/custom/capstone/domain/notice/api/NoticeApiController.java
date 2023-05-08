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

    @GetMapping("/write")
    public Long save(@RequestBody NoticeSaveRequestDto requestDto) {
        return noticeService.save(requestDto);
    }

    @PatchMapping("/{id}/edit")
    public Long update(@PathVariable Long id, @RequestBody NoticeUpdateRequestDto requestDto) {
        return noticeService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        noticeService.delete(id);
        return id;
    }

    @GetMapping("/{id}")
    public NoticeResponseDto findById(@PathVariable Long id) {
        return noticeService.findById(id);
    }
}
