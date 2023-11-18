package custom.capstone.domain.chat.dto.request;

public record ChatMessageSaveRequestDto (
        String roomId,
        String sender,
        String message
) {
}
