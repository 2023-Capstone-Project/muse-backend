package custom.capstone.domain.chat.dao;

import custom.capstone.domain.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findTop100ByRoomIdOrderByCreatedAtAsc(final String roomId);

    Message findTopByRoomIdOrderByCreatedAtDesc(final String roomId);
}