package custom.capstone.domain.magazine.dto.request;

public record MagazineSaveRequestDto (
        String title,
        String content,
        int views
) {
}
