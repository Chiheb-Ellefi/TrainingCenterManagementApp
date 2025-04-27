package com.example.CenterManagement.repositories.users.redisRepositories;

import com.example.CenterManagement.dto.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.Duration;

@Repository
public class UserCacheRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.application.ttl}")
    private Long ttl;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Autowired
    public UserCacheRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void addUserToCache(UserDto user) throws JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(user);
        redisTemplate.opsForValue().set("user:" + user.getUserId(), userJson, Duration.ofSeconds(ttl));
    }

    public UserDto getUserById(String userId) throws IOException {
        String userJson = redisTemplate.opsForValue().get("user:" + userId);
        if(userJson != null){
        return objectMapper.readValue(userJson, UserDto.class);
        }
       /* userJson=redisTemplate.opsForValue().get("participant:" + userId);
       if(userJson != null){
          return objectMapper.readValue(userJson, ParticipantDto.class).getUser();

       }
        userJson=redisTemplate.opsForValue().get("trainer:" + userId);
       if(userJson != null){
          return   objectMapper.readValue(userJson, TrainerDto.class).getUser();
       }*/
        return null;
    }
    public void addTokenToBlackList(String token) {
        redisTemplate.opsForSet().add("blacklist", token);
    }
    public boolean tokenInBlackList(String token) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("blacklist", token));
    }
    public void addResetCode(String email,String resetCode) {
        redisTemplate.opsForValue().set("resetCode:" + email,resetCode,Duration.ofSeconds(1800));
    }
    public Boolean resetCodeIsValid(String email,String resetCode) {
        String code = redisTemplate.opsForValue().get("resetCode:" + email);
        return  code != null && code.equals(resetCode);
    }
}