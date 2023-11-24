package custom.capstone.domain.chat.dao;

import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.members.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findByMemberOrReceiver(final Member member, final String receiver);
    ChatRoom findBySenderAndReceiver(final String nickname, final String receiver);
    ChatRoom findByRoomIdAndMemberOrRoomIdAndReceiver(final String roomId, final Member member, final String roomId1, final String nickname);
    ChatRoom findByRoomId(final String roomId);
}