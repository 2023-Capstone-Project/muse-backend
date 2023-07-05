package custom.capstone.domain.magazine.dto.response;

public record MagazineResponseDto (
        Long id,
        String title,
        String content,
        int views
) {
}
