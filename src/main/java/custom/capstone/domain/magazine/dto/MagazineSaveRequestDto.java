package custom.capstone.domain.magazine.dto;

public record MagazineSaveRequestDto (
        String title,
        String content,
        int views
) {
}
