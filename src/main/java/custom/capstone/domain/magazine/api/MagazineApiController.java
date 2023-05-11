package custom.capstone.domain.magazine.api;

import custom.capstone.domain.magazine.application.MagazineService;
import custom.capstone.domain.magazine.dto.MagazineResponseDto;
import custom.capstone.domain.magazine.dto.MagazineSaveRequestDto;
import custom.capstone.domain.magazine.dto.MagazineUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/magazine")
@RequiredArgsConstructor
public class MagazineApiController {
    private final MagazineService magazineService;

    @PostMapping("/write")
    public Long saveMagazine(@RequestBody MagazineSaveRequestDto requestDto) {
        return magazineService.saveMagazine(requestDto);
    }

    @PatchMapping("/{id}/edit")
    public Long updateMagazine(@PathVariable Long id, @RequestBody MagazineUpdateRequestDto requestDto) {
        return magazineService.updateMagazine(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteMagazine(@PathVariable Long id) {
        magazineService.deleteMagazine(id);
        return id;
    }

    @GetMapping("/{id}")
    public MagazineResponseDto findMagazineById(@PathVariable Long id) {
        return magazineService.findMagazineById(id);
    }
}
