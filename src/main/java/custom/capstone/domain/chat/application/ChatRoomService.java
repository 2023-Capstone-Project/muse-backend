package custom.capstone.domain.chat.application;

import custom.capstone.domain.chat.dao.MessageRepository;
import custom.capstone.domain.chat.dao.ChatRoomRepository;
import custom.capstone.domain.chat.domain.ChatRoom;
import custom.capstone.domain.chat.domain.Message;
import custom.capstone.domain.chat.dto.MessageRequestDto;
import custom.capstone.domain.chat.dto.MessageResponseDto;
import custom.capstone.domain.chat.dto.ChatRoomDto;
import custom.capstone.domain.chat.exception.ChatRoomNotFoundException;
import custom.capstone.domain.members.dao.MemberRepository;
import custom.capstone.domain.members.domain.Member;
import custom.capstone.domain.members.exception.MemberNotFoundException;
import custom.capstone.domain.posts.dao.PostRepository;
import custom.capstone.domain.posts.domain.Post;
import custom.capstone.domain.posts.exception.PostNotFoundException;
import custom.capstone.global.service.redis.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoomDto> opsHashChatRoom;
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
    public MessageResponseDto createRoom(final String loginEmail, final MessageRequestDto requestDto) {

        final Member member = memberRepository.findByEmail(loginEmail)
                .orElseThrow(MemberNotFoundException::new);

        final Post post = postRepository.findById(requestDto.postId()).
                orElseThrow(PostNotFoundException::new);

        ChatRoom chatRoom = chatRoomRepository.findBySenderAndReceiver(member.getNickname(), requestDto.receiver());

        if ((chatRoom == null) || (chatRoom != null
                        && (!member.getNickname().equals(chatRoom.getSender())
                        && !requestDto.receiver().equals(chatRoom.getReceiver())
                        && !requestDto.postId().equals(post.getId())))
        ) {
            ChatRoomDto messageRoomDto = ChatRoomDto.create(requestDto, member);

            opsHashChatRoom.put(CHAT_ROOMS, messageRoomDto.getRoomId(), messageRoomDto);

            chatRoom = chatRoomRepository.save(new ChatRoom(messageRoomDto.getSender(), messageRoomDto.getRoomId(), messageRoomDto.getReceiver(), member, post));
        }

        return new MessageResponseDto(chatRoom);
    }

    /**
     * 채팅방 목록 조회
     */
    public List<MessageResponseDto> findAll(final String loginEmail) {
        final Member member = memberRepository.findByEmail(loginEmail)
                .orElseThrow(MemberNotFoundException::new);

        final List<ChatRoom> chatRooms = chatRoomRepository.findByMemberOrReceiver(member, member.getNickname());

        List<MessageResponseDto> messageRoomDtos = new ArrayList<>();

        for (ChatRoom chatRoom : chatRooms) {
            //  member 가 sender 인 경우
            if (member.getNickname().equals(chatRoom.getSender())) {
                MessageResponseDto messageRoomDto = new MessageResponseDto(
                        chatRoom.getId(),
                        chatRoom.getMember().getId(),
                        chatRoom.getRoomId(),
                        chatRoom.getSender(),
                        chatRoom.getReceiver()
                );

                // 가장 최신 메시지 & 생성 시간 조회
                Message latestMessage = messageRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoom.getRoomId());

                if (latestMessage != null) {
                    messageRoomDto.setLatestMessageCreatedAt(latestMessage.getCreatedAt());
                    messageRoomDto.setLatestMessageContent(latestMessage.getMessage());
                }

                messageRoomDtos.add(messageRoomDto);

            } else {
                // member 가 receiver 인 경우
                MessageResponseDto messageRoomDto = new MessageResponseDto(
                        chatRoom.getId(),
                        chatRoom.getMember().getId(),
                        chatRoom.getRoomId(),
                        chatRoom.getSender(),
                        chatRoom.getReceiver());

                // 가장 최신 메시지 & 생성 시간 조회
                Message latestMessage = messageRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoom.getRoomId());
                if (latestMessage != null) {
                    messageRoomDto.setLatestMessageCreatedAt(latestMessage.getCreatedAt());
                    messageRoomDto.setLatestMessageContent(latestMessage.getMessage());
                }

                messageRoomDtos.add(messageRoomDto);
            }
        }

        return messageRoomDtos;
    }

    /**
     * 채팅방 단일 조회
     */
    public ChatRoomDto findRoom(final String loginEmail, final String roomId) {
        final Member member = memberRepository.findByEmail(loginEmail)
                .orElseThrow(MemberNotFoundException::new);

        ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        final Post post = postRepository.findById(chatRoom.getPost().getId())
                .orElseThrow(PostNotFoundException::new);

        final Member receiver = memberRepository.findById(post.getMember().getId())
                .orElseThrow(MemberNotFoundException::new);

        // sender & receiver 모두 messageRoom 조회 가능
        chatRoom = chatRoomRepository.findByRoomIdAndMemberOrRoomIdAndReceiver(roomId, member, roomId, receiver.getNickname());

        if (chatRoom == null) {
            throw new ChatRoomNotFoundException();
        }

        final ChatRoomDto chatRoomDto = new ChatRoomDto(
                chatRoom.getRoomId(),
                chatRoom.getSender(),
                chatRoom.getReceiver()
        );

        chatRoomDto.setChatRoomPostId(post.getId());

        return chatRoomDto;
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
     * 채팅방 삭제
     */
    public void deleteChatRoom(final String roomId) {
        final ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId);

        // TODO: 삭제하는 당사자가 누구인지 판별해서 삭제하기

        chatRoomRepository.delete(chatRoom);
    }

    /**
     * Redis 에서 채팅방 조회
     */
    public ChannelTopic getTopic(final String roomId) {
        return topics.get(roomId);
    }
}
