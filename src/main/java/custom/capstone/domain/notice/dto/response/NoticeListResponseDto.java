package custom.capstone.domain.notice.dto.response;

public record NoticeListResponseDto (
        Long id,
        String title,
        String content
) {
}
