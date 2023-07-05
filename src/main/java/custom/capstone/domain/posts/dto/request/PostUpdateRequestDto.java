package custom.capstone.domain.posts.dto.request;

public record PostUpdateRequestDto (
        String title,
        String content,
        int price
) {
}
