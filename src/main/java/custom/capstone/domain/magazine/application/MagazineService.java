package custom.capstone.domain.magazine.application;

import custom.capstone.domain.magazine.dao.MagazineRepository;
import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.magazine.dto.response.MagazineListResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineResponseDto;
import custom.capstone.domain.magazine.dto.request.MagazineSaveRequestDto;
import custom.capstone.domain.magazine.dto.request.MagazineUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MagazineService {
    private final MagazineRepository magazineRepository;

    @Transactional
    public Long saveMagazine(MagazineSaveRequestDto requestDto) {
        Magazine magazine = Magazine.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .views(requestDto.views())
                .build();

        return magazineRepository.save(magazine).getId();
    }

    @Transactional
    public Long updateMagazine(Long id, MagazineUpdateRequestDto requestDto) {
        Magazine magazine = magazineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 매거진이 없습니다. id = " + id));

        magazine.update(requestDto.title(), requestDto.content());

        return id;
    }

    public MagazineResponseDto findMagazineById(Long id) {
        Magazine entity = magazineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 매거진이 없습니다. id = " + id));

        return new MagazineResponseDto(entity.getId(), entity.getTitle(), entity.getContent(), entity.getViews());
    }

    @Transactional
    public List<MagazineListResponseDto> findAllDesc() {
        return magazineRepository.findAllDesc()
                .stream()
                .map(entity -> new MagazineListResponseDto(entity.getId(), entity.getTitle(), entity.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMagazine(Long id) {
        Magazine magazine = magazineRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 매거진이 없습니다. id = " + id));

        magazineRepository.delete(magazine);
    }

}
