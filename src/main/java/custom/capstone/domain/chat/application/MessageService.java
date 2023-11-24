package custom.capstone.domain.chat.application;

import custom.capstone.domain.chat.dao.MessageRepository;
import custom.capstone.domain.chat.dao.ChatRoomRepository;
import custom.capstone.domain.chat.domain.Message;
import custom.capstone.domain.chat.dto.MessageDto;
import custom.capstone.domain.members.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final RedisTemplate<String, MessageDto> redisTemplateMessage;
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅 저장
     */
    public void saveMessage(final MessageDto messageDto) {

        // DB 저장
        final Message message = new Message(messageDto.getSender(), messageDto.getRoomId(), messageDto.getMessage());

        messageRepository.save(message);

        // 직렬화
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));

        // redis 저장
        redisTemplateMessage.opsForList().rightPush(messageDto.getRoomId(), messageDto);

        // expire 을 이용해 Key 를 만료시키기
        redisTemplateMessage.expire(messageDto.getRoomId(), 1, TimeUnit.MINUTES);
    }

    /**
     * 채팅 조회
     */
    public List<MessageDto> loadMessage(final String roomId) {
        final List<MessageDto> messageList = new ArrayList<>();

        // Redis 에서 해당 채팅방의 메시지 100개 가져오기
        final List<MessageDto> redisMessageList = redisTemplateMessage.opsForList().range(roomId, 0, 99);

        // Redis 에서 가져온 메시지가 없다면, DB 에서 메시지 100개 가져오기
        if (redisMessageList == null || redisMessageList.isEmpty()) {
            final List<Message> dbMessageList = messageRepository.findTop100ByRoomIdOrderByCreatedAtAsc(roomId);

            for (Message message : dbMessageList) {
                MessageDto messageDto = new MessageDto(message);

                messageList.add(messageDto);

                redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Message.class));      // 직렬화
                redisTemplateMessage.opsForList().rightPush(roomId, messageDto);                                // redis 저장
            }
        } else {
            messageList.addAll(redisMessageList);
        }

        return messageList;
    }
}