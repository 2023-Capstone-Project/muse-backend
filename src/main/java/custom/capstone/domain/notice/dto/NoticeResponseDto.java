package custom.capstone.domain.notice.dto;

public record NoticeResponseDto (
        Long id,
        String title,
        String content,
        int views
) {
}
