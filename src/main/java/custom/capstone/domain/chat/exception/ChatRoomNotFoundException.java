package custom.capstone.domain.chat.exception;

import custom.capstone.global.exception.BaseResponseStatus;
import custom.capstone.global.exception.BusinessException;

public class ChatRoomNotFoundException extends BusinessException {
    public ChatRoomNotFoundException(){
        super(BaseResponseStatus.CHATROOM_NOT_FOUND);
    }
}
