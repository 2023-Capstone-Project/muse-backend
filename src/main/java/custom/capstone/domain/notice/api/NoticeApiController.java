package custom.capstone.domain.notice.api;

import custom.capstone.domain.notice.application.NoticeService;
import custom.capstone.domain.notice.dto.response.NoticeResponseDto;
import custom.capstone.domain.notice.dto.request.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.request.NoticeUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지사항 API")
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeApiController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 생성")
    @PostMapping("/write")
    public Long saveNotice(@RequestBody final NoticeSaveRequestDto requestDto) {
        return noticeService.saveNotice(requestDto);
    }

    @Operation(summary = "공지사항 수정")
    @PatchMapping("/{noticeId}/edit")
    public Long updateNotice(@PathVariable("noticeId") final Long id,
                             @RequestBody final NoticeUpdateRequestDto requestDto) {
        return noticeService.updateNotice(id, requestDto);
    }

    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{noticeId}")
    public Long deleteNotice(@PathVariable("noticeId") final Long id) {
        noticeService.deleteNotice(id);
        return id;
    }

    @Operation(summary = "공지사항 조회")
    @GetMapping("/{noticeId}")
    public NoticeResponseDto findNoticeById(@PathVariable("noticeId") final Long id) {
        return noticeService.findNoticeById(id);
    }
}
