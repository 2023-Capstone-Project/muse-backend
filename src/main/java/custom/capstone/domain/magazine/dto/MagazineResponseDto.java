package custom.capstone.domain.magazine.dto;

import custom.capstone.domain.admin.Admin;

public record MagazineResponseDto (
        Long id,
        String title,
        String content,
        Admin admin,
        int views
) {
}
