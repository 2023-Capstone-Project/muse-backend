package custom.capstone.domain.notice.dto;

import custom.capstone.domain.admin.Admin;

public record NoticeResponseDto (
        Long id,
        String title,
        String content,
        Admin admin,
        int views
) {
}
