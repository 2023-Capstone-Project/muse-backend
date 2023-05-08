package custom.capstone.domain.magazine.dto;

import custom.capstone.domain.admin.Admin;

public record MagazineListResponseDto (
        Long id,
        String title,
        String content,
        Admin admin
) {
}
