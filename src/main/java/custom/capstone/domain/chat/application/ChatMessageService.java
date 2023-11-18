package custom.capstone.domain.chat.application;

import custom.capstone.domain.chat.dao.ChatMessageRepository;
import custom.capstone.domain.chat.dao.ChatRoomRepository;
import custom.capstone.domain.chat.domain.ChatMessage;
import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.chat.dto.request.ChatMessageSaveRequestDto;
import custom.capstone.domain.chat.dto.response.ChatMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final RedisTemplate<String, ChatMessage> redisTemplateMessage;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅 저장
     */
    @Transactional
    public void saveChatMessage(final ChatMessageSaveRequestDto requestDto) {
        final ChatRoom chatRoom = chatRoomRepository.findByRoomId(requestDto.roomId());

        final ChatMessage chatMessage = ChatMessage.builder()
                .roomId(requestDto.roomId())
                .sender(requestDto.sender())
                .message(requestDto.message())
                .chatRoom(chatRoom)
                .build();

        chatMessageRepository.save(chatMessage);

        // 직렬화
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));

        // redis 저장
        redisTemplateMessage.opsForList().rightPush(chatMessage.getRoomId(), chatMessage);

        // expire 을 이용해 Key 만료시키기
        redisTemplateMessage.expire(chatMessage.getRoomId(), 1, TimeUnit.MINUTES);
    }

    /**
     * 채팅 조회
     */
    public List<ChatMessageResponseDto> loadChatMessage(final String roomId) {
        final List<ChatMessage> chatMessages = redisTemplateMessage.opsForList().range(roomId, 0,  99);

        return chatMessages.stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
    }
}
