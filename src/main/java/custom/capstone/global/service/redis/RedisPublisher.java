package custom.capstone.global.service.redis;

import custom.capstone.domain.chat.dto.request.ChatMessageSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageSaveRequestDto requestDto) {
        redisTemplate.convertAndSend(topic.getTopic(), requestDto);
    }
}
