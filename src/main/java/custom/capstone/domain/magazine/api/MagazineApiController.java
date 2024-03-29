package custom.capstone.domain.magazine.api;

import custom.capstone.domain.magazine.application.MagazineService;
import custom.capstone.domain.magazine.dto.response.MagazineListResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineResponseDto;
import custom.capstone.domain.magazine.dto.request.MagazineSaveRequestDto;
import custom.capstone.domain.magazine.dto.request.MagazineUpdateRequestDto;
import custom.capstone.domain.magazine.dto.response.MagazineSaveResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineUpdateResponseDto;
import custom.capstone.global.common.BaseResponse;
import custom.capstone.global.exception.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Tag(name = "매거진 API")
@RestController
@RequestMapping("/api/magazine")
@RequiredArgsConstructor
public class MagazineApiController {
    private final MagazineService magazineService;

    @Operation(summary = "매거진 등록")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public BaseResponse<MagazineSaveResponseDto> saveMagazine(
            @AuthenticationPrincipal final String loginEmail,
            @RequestPart(value = "imageUrls", required = false) final List<MultipartFile> images,
            @Valid @RequestPart("requestDto") final MagazineSaveRequestDto requestDto
    ) throws IOException {
        final MagazineSaveResponseDto result = magazineService.saveMagazine(loginEmail, images, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "매거진 수정")
    @PatchMapping("/{magazineId}")
    public BaseResponse<MagazineUpdateResponseDto> updateMagazine(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("magazineId") final Long id,
            @RequestBody final MagazineUpdateRequestDto requestDto
    ) {
        final MagazineUpdateResponseDto result = magazineService.updateMagazine(loginEmail, id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "매거진 삭제")
    @DeleteMapping("/{magazineId}")
    public Long deleteMagazine(
            @AuthenticationPrincipal final String loginEmail,
            @PathVariable("magazineId") final Long id
    ) {
        magazineService.deleteMagazine(loginEmail, id);
        return id;
    }

    @Operation(summary = "매거진 페이징 조회")
    @GetMapping
    public BaseResponse<Page<MagazineListResponseDto>> findAll(@PageableDefault(size = 20) final Pageable pageable) {
        final Page<MagazineListResponseDto> result = magazineService.findAll(pageable);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_READ_SUCCESS,
                result
        );
    }

    @Operation(summary = "매거진 상세 조회")
    @GetMapping("/{magazineId}")
    public BaseResponse<MagazineResponseDto> findDetailById(@PathVariable("magazineId") final Long id) {
        final MagazineResponseDto result = magazineService.findDetailById(id);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_DETAIL_READ_SUCCESS,
                result
        );
    }
}
