package custom.capstone.domain.chat.application;

import custom.capstone.domain.chat.dao.ChatMessageRepository;
import custom.capstone.domain.chat.dao.ChatRoomRepository;
import custom.capstone.domain.chat.domain.ChatMessage;
import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.chat.dto.request.ChatMessageSaveRequestDto;
import custom.capstone.domain.chat.dto.response.ChatMessageResponseDto;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.exception.MemberNotFoundException;
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
    private final MemberRepository memberRepository;

    /**
     * 채팅 저장
     */
    @Transactional
    public Long saveChatMessage(final ChatMessageSaveRequestDto requestDto) {

        final Member sender = memberRepository.findById(requestDto.senderId())
                .orElseThrow(MemberNotFoundException::new);

        final ChatRoom chatRoom = chatRoomRepository.findByRoomId(requestDto.roomId());

        // TODO: Receiver = sender 가 보내는 receiver

        System.out.println("ChatRoom: " + chatRoom.getRoomId());

        final ChatMessage chatMessage = ChatMessage.builder()
                .roomId(requestDto.roomId())
                .sender(sender.getNickname())
                .message(requestDto.message())
                .chatRoom(chatRoom)
                .build();

        chatRoom.getMessageList().add(chatMessage);
        chatMessageRepository.save(chatMessage);

        // 직렬화
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));

        // redis 저장
        redisTemplateMessage.opsForList().rightPush(chatMessage.getRoomId(), chatMessage);

        // expire 을 이용해 Key 만료시키기
        redisTemplateMessage.expire(chatMessage.getRoomId(), 1, TimeUnit.MINUTES);

        System.out.println("보내는 아이디: " + sender.getId());

        return sender.getId();
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
