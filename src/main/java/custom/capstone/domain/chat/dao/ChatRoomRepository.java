package custom.capstone.domain.chat.dao;

import custom.capstone.domain.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findBySenderAndReceiver(final String sender, String receiver);
    ChatRoom findByRoomId(final String roomId);
}
