package custom.capstone.domain.magazine.dto;

public record MagazineResponseDto (
        Long id,
        String title,
        String content,
        int views
) {
}
