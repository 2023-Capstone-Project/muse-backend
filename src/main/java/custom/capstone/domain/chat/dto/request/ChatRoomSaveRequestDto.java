package custom.capstone.domain.chat.dto.request;

public record ChatRoomSaveRequestDto (
        Long postId,
        Long receiverId
//        String receiver
) {
}

//        String sender,  // TODO: 로그인 한 사용자로 가져오기
