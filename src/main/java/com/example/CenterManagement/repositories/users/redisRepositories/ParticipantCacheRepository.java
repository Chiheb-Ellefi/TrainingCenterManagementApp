package com.example.CenterManagement.repositories.users.redisRepositories;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class ParticipantCacheRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.application.ttl}")
    private Long ttl;
    @Autowired
    ParticipantCacheRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
    public void addParticipantToCache(ParticipantDto participantDto) throws JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(participantDto);
        redisTemplate.opsForValue().set("participant:" + participantDto.getParticipantId(), userJson, Duration.ofSeconds(ttl));
    }
    public boolean removeParticipantFromCache(ParticipantDto participantDto)  {
      return   redisTemplate.delete("participant:" + participantDto.getParticipantId());
    }
    public ParticipantDto getParticipantFromCache(String participantId) throws JsonProcessingException {
        String userJson = redisTemplate.opsForValue().get("participant:" + participantId);
        if (userJson == null) {
            return null;
        }
        return objectMapper.readValue(userJson, ParticipantDto.class);
    }

}
