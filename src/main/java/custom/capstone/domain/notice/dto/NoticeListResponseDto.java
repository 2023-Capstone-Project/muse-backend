package custom.capstone.domain.notice.dto;

import custom.capstone.domain.admin.Admin;

public record NoticeListResponseDto (
        Long id,
        String title,
        String content,
        Admin admin
) {
}
