package custom.capstone.domain.chat.dto.request;

public record ChatMessageSaveRequestDto (
        String roomId,
        Long senderId,
        String message
) {
}
