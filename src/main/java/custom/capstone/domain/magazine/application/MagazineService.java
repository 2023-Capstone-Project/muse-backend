package custom.capstone.domain.magazine.application;

import custom.capstone.domain.magazine.dao.MagazineRepository;
import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.magazine.dto.request.MagazineSaveRequestDto;
import custom.capstone.domain.magazine.dto.request.MagazineUpdateRequestDto;
import custom.capstone.domain.magazine.dto.response.MagazineListResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineResponseDto;
import custom.capstone.domain.magazine.exception.MagazineNotFoundException;
import custom.capstone.domain.members.application.MemberService;
import custom.capstone.domain.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MagazineService {
    private final MagazineRepository magazineRepository;
    private final MemberService memberService;

    /**
     * 매거진 등록
     */
    @Transactional
    public Long saveMagazine(final MagazineSaveRequestDto requestDto) {
        final Member member = memberService.findById(requestDto.memberId());
        final Magazine magazine = Magazine.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .member(member)
                .build();

        return magazineRepository.save(magazine).getId();
    }

    /**
     * 매거진 수정
     */
    @Transactional
    public Long updateMagazine(final Long magazineId, final MagazineUpdateRequestDto requestDto) {
        final Magazine magazine = magazineRepository.findById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);

        magazine.update(requestDto.title(), requestDto.content());

        return magazineId;
    }

    /**
     * 매거진 페이징 조회
     */
    public Page<MagazineListResponseDto> findAll(final Pageable pageable) {
        return magazineRepository.findAll(pageable)
                .map(MagazineListResponseDto::new);
    }

    public Magazine findById(final Long magazineId) {
        return magazineRepository.findById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);
    }

    /*
     * 매거진 상세 조회
     */
    public MagazineResponseDto findDetailById(final Long magazineId) {
        final Magazine entity = magazineRepository.findDetailById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);

        return new MagazineResponseDto(entity);
    }

    /**
     * 매거진 삭제
     */
    @Transactional
    public void deleteMagazine(final Long magazineId) {
        final Magazine magazine = magazineRepository.findById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);

        magazineRepository.delete(magazine);
    }
}
