package custom.capstone.domain.magazine.application;

import custom.capstone.domain.magazine.dao.MagazineRepository;
import custom.capstone.domain.magazine.domain.Magazine;
import custom.capstone.domain.magazine.dto.request.MagazineSaveRequestDto;
import custom.capstone.domain.magazine.dto.request.MagazineUpdateRequestDto;
import custom.capstone.domain.magazine.dto.response.MagazineListResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineSaveResponseDto;
import custom.capstone.domain.magazine.dto.response.MagazineUpdateResponseDto;
import custom.capstone.domain.magazine.exception.MagazineInvalidException;
import custom.capstone.domain.magazine.exception.MagazineNotFoundException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MagazineService {
    private final MagazineRepository magazineRepository;
    private final MemberRepository memberRepository;

    /**
     * 매거진 등록
     */
    @Transactional
    public MagazineSaveResponseDto saveMagazine(final String loginEmail, final MagazineSaveRequestDto requestDto) {
        final Member admin = getValidAdmin(loginEmail);

        final Magazine magazine = Magazine.builder()
                .title(requestDto.title())
                .content(requestDto.content())
                .member(admin)
                .build();

        magazineRepository.save(magazine);

        return new MagazineSaveResponseDto(magazine.getId());
    }

    /**
     * 매거진 수정
     */
    @Transactional
    public MagazineUpdateResponseDto updateMagazine(
            final String loginEmail,
            final Long magazineId,
            final MagazineUpdateRequestDto requestDto
    ) {
        final Member admin = getValidAdmin(loginEmail);

        final Magazine magazine = magazineRepository.findById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);

        checkEqualAdmin(admin, magazine);

        magazine.update(requestDto.title(), requestDto.content());

        return new MagazineUpdateResponseDto(magazineId);
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

    /**
     * 매거진 상세 조회
     */
    @Transactional
    public MagazineResponseDto findDetailById(final Long magazineId) {
        final Magazine magazine = magazineRepository.findDetailById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);

        magazine.increaseView();

        return new MagazineResponseDto(magazine);
    }

    /**
     * 매거진 삭제
     */
    @Transactional
    public void deleteMagazine(final String loginEmail, final Long magazineId) {
        final Member admin = getValidAdmin(loginEmail);

        final Magazine magazine = magazineRepository.findById(magazineId)
                .orElseThrow(MagazineNotFoundException::new);

        checkEqualAdmin(admin, magazine);

        magazineRepository.delete(magazine);
    }

    // 관리자인지 확인
    private Member getValidAdmin(final String loginEmail) {
        return  memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new UsernameNotFoundException("관리자를 찾을 수 없습니다."));
    }

    // 작성한 관리자가 맞는지 확인
    private void checkEqualAdmin(final Member admin, final Magazine magazine) {
        if (!magazine.getMember().getId().equals(admin.getId()))
            throw new MagazineInvalidException();
    }
}
