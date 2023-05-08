package custom.capstone.domain.notice.dto;

public record NoticeSaveRequestDto (
   String title,
   String content,
   int views
) {
}
