package custom.capstone.domain.notice.api;

import custom.capstone.domain.notice.application.NoticeService;
import custom.capstone.domain.notice.dto.response.NoticeListResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeResponseDto;
import custom.capstone.domain.notice.dto.request.NoticeSaveRequestDto;
import custom.capstone.domain.notice.dto.request.NoticeUpdateRequestDto;
import custom.capstone.domain.notice.dto.response.NoticeSaveResponseDto;
import custom.capstone.domain.notice.dto.response.NoticeUpdateResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "공지사항 API")
@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeApiController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록")
    @PostMapping
    public BaseResponse<NoticeSaveResponseDto> saveNotice(
            @AuthenticationPrincipal final String loginEmail,
            @RequestPart("imageUrls") final List<MultipartFile> images,
            @Valid @RequestPart("requestDto") final NoticeSaveRequestDto requestDto
    ) throws IOException {
        final NoticeSaveResponseDto result = noticeService.saveNotice(loginEmail, images, requestDto);

        return  BaseResponse.of(
                BaseResponseStatus.NOTICE_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "공지사항 수정")
    @PatchMapping("/{noticeId}")
    public BaseResponse<NoticeUpdateResponseDto> updateNotice(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("noticeId") final Long id,
            @RequestBody final NoticeUpdateRequestDto requestDto
    ) {
        final NoticeUpdateResponseDto result = noticeService.updateNotice(loginEmail, id, requestDto);

        return  BaseResponse.of(
                BaseResponseStatus.NOTICE_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{noticeId}")
    public Long deleteNotice(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("noticeId") final Long id
    ) {
        noticeService.deleteNotice(loginEmail, id);
        return id;
    }

    @Operation(summary = "공지사항 페이징 조회")
    @GetMapping
    public BaseResponse<Page<NoticeListResponseDto>> findAll(@PageableDefault(size = 20) final Pageable pageable) {
        final Page<NoticeListResponseDto> result = noticeService.findAll(pageable);

        return BaseResponse.of(
                BaseResponseStatus.NOTICE_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "공지사항 상세 조회")
    @GetMapping("/{noticeId}")
    public BaseResponse<NoticeResponseDto> findNoticeById(@PathVariable("noticeId") final Long id) {
        final NoticeResponseDto result = noticeService.findDetailById(id);

        return BaseResponse.of(
                BaseResponseStatus.NOTICE_DETAIL_READ_SUCCESS,
                result
        );
    }
}
