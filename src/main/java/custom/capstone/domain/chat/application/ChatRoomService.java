package custom.capstone.domain.chat.application;

import custom.capstone.domain.chat.dao.ChatMessageRepository;
import custom.capstone.domain.chat.dao.ChatRoomRepository;
import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.chat.dto.request.ChatRoomSaveRequestDto;
import custom.capstone.domain.chat.dto.response.ChatRoomResponseDto;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.exception.MemberNotFoundException;
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
    public ChatRoomResponseDto saveChatRoom(final String loginEmail, final ChatRoomSaveRequestDto requestDto)
            throws IllegalAccessException {

        Post post = postRepository.findById(requestDto.postId())
                .orElseThrow(PostNotFoundException::new);

        Member sender = memberRepository.findByEmail(loginEmail)
                .orElseThrow(MemberNotFoundException::new);

        Member receiver = memberRepository.findById(requestDto.receiverId())
                .orElseThrow(MemberNotFoundException::new);

        checkForChatRoomDuplication(post, sender, receiver);

        ChatRoom savedChatRoom = ChatRoom.builder()
                .post(post)
                .sender(sender)
                .receiver(receiver)
                .build();

        chatRoomRepository.save(savedChatRoom);
        opsHashChatRoom.put(CHAT_ROOMS, savedChatRoom.getRoomId(), savedChatRoom);

        return new ChatRoomResponseDto(savedChatRoom);
    }

    private void checkForChatRoomDuplication(
            final Post post,
            final Member sender,
            final Member receiver
    ) throws IllegalAccessException {

        System.out.println("sender: " + sender.getNickname()+ " receiver: " + receiver.getNickname());

        if (sender.getNickname().equals(receiver.getNickname())) {
            throw new IllegalAccessException("자신과의 채팅은 생성할 수 없습니다.");
        }

        ChatRoom chatRoom = chatRoomRepository.findBySenderAndReceiver(sender, receiver);

        if (chatRoom != null && (!sender.getNickname().equals(chatRoom.getSender())
                        && !receiver.getNickname().equals(chatRoom.getReceiver())
                        && !post.getId().equals(chatRoom.getId()))) {
            throw new IllegalAccessException("이미 해당 게시글로 만들어진 채팅방 중 sender와 receiver로 이루어진 채팅방이 있습니다.");
        }
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
