package custom.capstone.domain.chat.dao;

import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findBySenderAndReceiver(final Member sender, Member receiver);
    ChatRoom findByRoomId(final String roomId);
}
