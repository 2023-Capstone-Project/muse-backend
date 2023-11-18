package custom.capstone.domain.chat.application;

import custom.capstone.domain.chat.dao.ChatMessageRepository;
import custom.capstone.domain.chat.dao.ChatRoomRepository;
import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.chat.dto.request.ChatRoomSaveRequestDto;
import custom.capstone.domain.chat.dto.response.ChatRoomResponseDto;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.exception.PostNotFoundException;
import custom.capstone.global.service.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private Map<String, ChannelTopic> topics;

    /**
     * redis 의 Hash 데이터 다루기
     */
    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    /**
     * 채팅방 생성
     */
    @Transactional
    public ChatRoomResponseDto saveChatRoom(final ChatRoomSaveRequestDto requestDto) {

        // TODO: 로그인 한 사용자 = sender, 게시글 디자이너 = receiver

        final Post post = postRepository.findById(requestDto.postId())
                .orElseThrow(PostNotFoundException::new);

        ChatRoom chatRoom = chatRoomRepository.findBySenderAndReceiver(requestDto.sender(), requestDto.receiver());

        if (chatRoom == null) {
            chatRoom = ChatRoom.builder()
                    .post(post)
                    .sender(requestDto.sender())
                    .receiver(requestDto.receiver())
                    .build();

            opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getRoomId(), chatRoom);

            chatRoomRepository.save(chatRoom);
        }

        return new ChatRoomResponseDto(chatRoom);
    }

    /**
     * 채팅방 목록 조회
     */
    public List<ChatRoomResponseDto> findAll() {
        final List<ChatRoom> chatRooms =  chatRoomRepository.findAll();

        return chatRooms.stream()
                .map(ChatRoomResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 채팅방 단일 조회
     */
    public ChatRoomResponseDto findById(final String roomId) {
        final ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        final Post post = postRepository.findById(chatRoom.getPost().getId())
                .orElseThrow(PostNotFoundException::new);

        // TODO: 사용자 확인 추가

        return new ChatRoomResponseDto(chatRoom);
    }

    /**
     * 채팅방 삭제
     */
    @Transactional
    public void deleteChatRoom(final String roomId) {
        final ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        // TODO: 삭제하는 당사자가 누구인지 판별해서 삭제하기

        chatRoomRepository.delete(chatRoom);
    }

    /**
     * 채팅방 입장
     */
    public void enterChatRoom(final String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    /**
     * Redis 에서 채팅방 조회
     */
    public ChannelTopic getTopic(final String roomId) {
        return topics.get(roomId);
    }
}
