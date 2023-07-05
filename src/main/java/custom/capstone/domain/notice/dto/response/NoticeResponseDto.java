package custom.capstone.domain.notice.dto.response;

public record NoticeResponseDto (
        Long id,
        String title,
        String content,
        int views
) {
}
