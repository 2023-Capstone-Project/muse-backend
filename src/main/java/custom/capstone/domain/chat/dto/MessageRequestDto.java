package custom.capstone.domain.chat.dto;

public record MessageRequestDto(
        Long postId,
        String receiver
) {
}
