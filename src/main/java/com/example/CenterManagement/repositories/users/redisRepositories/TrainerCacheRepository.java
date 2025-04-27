package com.example.CenterManagement.repositories.users.redisRepositories;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class TrainerCacheRepository {
 private final RedisTemplate<String, String> redisTemplate;
 private final ObjectMapper objectMapper;
    @Value("${spring.application.ttl}")
    private Long ttl;
 @Autowired
 public TrainerCacheRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
     this.redisTemplate = redisTemplate;
     this.objectMapper = objectMapper;
 }
 public TrainerDto getTrainerById(String id) throws JsonProcessingException {
     String userJson = redisTemplate.opsForValue().get("trainer:" + id);
     if(userJson != null){
         return objectMapper.readValue(userJson, TrainerDto.class);
     }
     return null;
 }
    public void addTrainerToCache(TrainerDto trainer) throws JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(trainer);
        redisTemplate.opsForValue().set("trainer:" + trainer.getTrainerId(), userJson, Duration.ofSeconds(ttl));
    }
}
