package custom.capstone.domain.chat.dto.request;

public record ChatMessageRequestDto (
        String channelTopic,
        String message
) {
}
