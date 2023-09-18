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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "매거진 API")
@RestController
@RequestMapping("/api/magazine")
@RequiredArgsConstructor
public class MagazineApiController {
    private final MagazineService magazineService;

    @Operation(summary = "매거진 등록")
    @PostMapping
    public BaseResponse<MagazineSaveResponseDto> saveMagazine(@Valid @RequestBody final MagazineSaveRequestDto requestDto) {
        final MagazineSaveResponseDto result = magazineService.saveMagazine(requestDto);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_SAVE_SUCCESS,
                result
        );
    }

    @Operation(summary = "매거진 수정")
    @PatchMapping("/{magazineId}")
    public BaseResponse<MagazineUpdateResponseDto> updateMagazine(@PathVariable("magazineId") final Long id,
                                                                  @RequestBody final MagazineUpdateRequestDto requestDto) {
        final MagazineUpdateResponseDto result = magazineService.updateMagazine(id, requestDto);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_UPDATE_SUCCESS,
                result
        );
    }

    @Operation(summary = "매거진 삭제")
    @DeleteMapping("/{magazineId}")
    public Long deleteMagazine(@PathVariable("magazineId") final Long id) {
        magazineService.deleteMagazine(id);
        return id;
    }

    @Operation(summary = "매거진 페이징 조회")
    @GetMapping
    public Page<MagazineListResponseDto> findAll(final Pageable pageable) {
        return magazineService.findAll(pageable);
    }

    @Operation(summary = "매거진 상세 조회")
    @GetMapping("/{magazineId}")
    public BaseResponse<MagazineResponseDto> findDetailById(@PathVariable("magazineId") final Long id) {
        final MagazineResponseDto result = magazineService.findDetailById(id);

        return BaseResponse.of(
                BaseResponseStatus.MAGAZINE_READ_SUCCESS,
                result
        );
    }
}
