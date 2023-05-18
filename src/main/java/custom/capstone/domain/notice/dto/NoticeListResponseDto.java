package custom.capstone.domain.notice.dto;

public record NoticeListResponseDto (
        Long id,
        String title,
        String content,
) {
}
