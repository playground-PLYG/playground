package com.playground.api.sample.service;


import com.playground.api.sample.entity.RedisPublishEntity;
import com.playground.api.sample.entity.RedisRepositoryEntity;
import com.playground.api.sample.entity.RedisTemplateEntity;
import com.playground.api.sample.repository.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {
  private final RedisRepository redisRepository;
  private final ModelMapper modelMapper;
  private final RedisTemplate<String, Object> redisTemplate;
  private static final String TEMPLATE_KEY = "RedisTemplateEntity";

  public RedisRepositoryEntity putRepository(RedisRepositoryEntity param) {
    param.setCreatedAt(LocalDateTime.now());

    return redisRepository.save(param);
  }

  public RedisRepositoryEntity getRepository(String id) {
    return redisRepository.findById(id).orElseGet(RedisRepositoryEntity::new);
  }

  public long getCountRepository() {
    return redisRepository.count();
  }

  public List<RedisRepositoryEntity> getAllRepository() {
    return redisRepository.findAll();
  }

  public void deleteRepository(RedisRepositoryEntity param) {
    redisRepository.delete(param);
  }

  public void deleteByIdRepository(String id) {
    redisRepository.deleteById(id);
  }

  public void putTemplate(RedisTemplateEntity param) {
    param.setCreatedAt(LocalDateTime.now());

    redisTemplate.opsForHash().put(TEMPLATE_KEY, param.getId(), param);
  }

  public RedisTemplateEntity getTemplate(String id) {
    return (RedisTemplateEntity) redisTemplate.opsForHash().get(TEMPLATE_KEY, id);
  }

  public long getCountTemplate() {
    Set<Object> keySet = redisTemplate.opsForHash().keys(TEMPLATE_KEY);

    if (CollectionUtils.isEmpty(keySet)) {
      return 0;
    } else {
      return keySet.size();
    }
  }

  public List<RedisTemplateEntity> getAllTemplate() {
    Map<Object, Object> hashEntries = redisTemplate.opsForHash().entries(TEMPLATE_KEY);
    List<RedisTemplateEntity> resultList;

    if (CollectionUtils.isEmpty(hashEntries)) {
      resultList = new ArrayList<>();
    } else {
      resultList = hashEntries.values().stream().map(o -> modelMapper.map(o, RedisTemplateEntity.class)).toList();
    }

    return resultList;
  }

  public void deleteTemplate(RedisTemplateEntity param) {
    redisTemplate.opsForHash().delete(TEMPLATE_KEY, param.getId());
  }

  public void deleteByIdTemplate(String id) {
    redisTemplate.opsForHash().delete(TEMPLATE_KEY, id);
  }

  public void publishTopic(RedisPublishEntity redisPublishEntity) {
    redisTemplate.convertAndSend(redisPublishEntity.getTopic(), redisPublishEntity.getMessage());
  }
}
