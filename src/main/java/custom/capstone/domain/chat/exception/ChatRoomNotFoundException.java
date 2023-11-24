package custom.capstone.domain.chat.exception;

import custom.capstone.global.exception.BaseException;
import custom.capstone.global.exception.BaseResponseStatus;

public class ChatRoomNotFoundException extends BaseException {
    public ChatRoomNotFoundException(){
        super(BaseResponseStatus.CHATROOM_NOT_FOUND);
    }
}
