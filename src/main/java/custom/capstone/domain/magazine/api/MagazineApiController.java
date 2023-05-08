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

    @GetMapping("/write")
    public Long save(@RequestBody MagazineSaveRequestDto requestDto) {
        return magazineService.save(requestDto);
    }

    @PatchMapping("/{id}/edit")
    public Long update(@PathVariable Long id, @RequestBody MagazineUpdateRequestDto requestDto) {
        return magazineService.update(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        magazineService.delete(id);
        return id;
    }

    @GetMapping("/{id}")
    public MagazineResponseDto findById(@PathVariable Long id) {
        return magazineService.findById(id);
    }
}
