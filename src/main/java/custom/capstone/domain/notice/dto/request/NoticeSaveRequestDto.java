package custom.capstone.domain.notice.dto.request;

public record NoticeSaveRequestDto (
   String title,
   String content,
   int views
) {
}
