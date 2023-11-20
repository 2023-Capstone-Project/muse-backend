package custom.capstone.global.config;

import custom.capstone.domain.chat.domain.ChatMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * redis pub/sub 메시지를 처리하는 listener 설정
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);

        return container;
    }

    /**
     * 애플리케이션에서 사용할 redisTemplate 설정
     *
     * JSON 형식을 위해 직렬화
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));

        return redisTemplate;
    }

    /**
     * redis 에 메시지 내역을 저장하기 위한 redisTamplte 설정
     */
    @Bean
    public RedisTemplate<String, ChatMessage> redisTemplateMessage(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, ChatMessage> redisTemplateMessage = new RedisTemplate<>();

        redisTemplateMessage.setConnectionFactory(connectionFactory);
        redisTemplateMessage.setKeySerializer(new StringRedisSerializer());
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));

        return redisTemplateMessage;
    }
}