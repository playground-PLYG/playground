package com.playground.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.playground.constants.RedisSubscibeChannel;
import com.playground.listener.RedisSeverSentEventsMessageSubscribeListener;
import com.playground.listener.RedisWebSocketMessageSubscribeListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableRedisRepositories
public class RedisConfig {

  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Value("${spring.data.redis.password}")
  private String redisPassword;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

    log.debug(">>> redisHost : {}", redisHost);
    log.debug(">>> redisPort : {}", redisPort);

    redisStandaloneConfiguration.setHostName(redisHost);
    redisStandaloneConfiguration.setPort(redisPort);
    redisStandaloneConfiguration.setPassword(redisPassword);

    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper());


    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(stringRedisSerializer);
    redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
    redisTemplate.setHashKeySerializer(stringRedisSerializer);
    redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

    return redisTemplate;
  }

  @Bean
  public RedisMessageListenerContainer redisContainer(MessageListenerAdapter websocketMessageListener,
      MessageListenerAdapter serverSentEventsMessageListener) {
    RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    container.setConnectionFactory(redisConnectionFactory());
    container.addMessageListener(websocketMessageListener, webSocketTopic());
    container.addMessageListener(serverSentEventsMessageListener, serverSentEventsTopic());

    return container;
  }

  @Bean
  public MessageListenerAdapter websocketMessageListener(RedisWebSocketMessageSubscribeListener redisWebSocketMessageSubscribeListener) {
    return new MessageListenerAdapter(redisWebSocketMessageSubscribeListener);
  }

  @Bean
  public MessageListenerAdapter serverSentEventsMessageListener(
      RedisSeverSentEventsMessageSubscribeListener redisSeverSentEventsMessageSubscribeListener) {
    return new MessageListenerAdapter(redisSeverSentEventsMessageSubscribeListener);
  }

  @Bean
  public ChannelTopic webSocketTopic() {
    return new ChannelTopic(RedisSubscibeChannel.WEBSOCKET_TOPIC.name());
  }

  @Bean
  public ChannelTopic serverSentEventsTopic() {
    return new ChannelTopic(RedisSubscibeChannel.SSE_TOPIC.name());
  }

  private ObjectMapper objectMapper() {
    PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().allowIfSubType(Object.class).build();
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

    return objectMapper;
  }
}
