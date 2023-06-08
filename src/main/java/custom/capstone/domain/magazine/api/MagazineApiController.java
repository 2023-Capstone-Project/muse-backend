package custom.capstone.domain.magazine.api;

import custom.capstone.domain.magazine.application.MagazineService;
import custom.capstone.domain.magazine.dto.MagazineResponseDto;
import custom.capstone.domain.magazine.dto.MagazineSaveRequestDto;
import custom.capstone.domain.magazine.dto.MagazineUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "매거진 API")
@RestController
@RequestMapping("/api/magazine")
@RequiredArgsConstructor
public class MagazineApiController {
    private final MagazineService magazineService;

    @Operation(summary = "매거진 생성")
    @PostMapping("/write")
    public Long saveMagazine(@RequestBody final MagazineSaveRequestDto requestDto) {
        return magazineService.saveMagazine(requestDto);
    }

    @Operation(summary = "매거진 수정")
    @PatchMapping("/{magazineId}/edit")
    public Long updateMagazine(@PathVariable("magazineId") final Long id,
                               @RequestBody final MagazineUpdateRequestDto requestDto) {
        return magazineService.updateMagazine(id, requestDto);
    }

    @Operation(summary = "매거진 삭제")
    @DeleteMapping("/{magazineId}")
    public Long deleteMagazine(@PathVariable("magazineId") final Long id) {
        magazineService.deleteMagazine(id);
        return id;
    }

    @Operation(summary = "매거진 조회")
    @GetMapping("/{magazineId}")
    public MagazineResponseDto findMagazineById(@PathVariable("magazineId") final Long id) {
        return magazineService.findMagazineById(id);
    }
}
