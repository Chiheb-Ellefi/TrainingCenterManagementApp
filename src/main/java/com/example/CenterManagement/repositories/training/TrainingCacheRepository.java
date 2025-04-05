package com.example.CenterManagement.repositories.training;

import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class TrainingCacheRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    @Value("${spring.application.ttl}")
    private Long ttl;
    @Autowired
    public TrainingCacheRepository(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void addTrainingToCache(TrainingDto trainingDto)throws JsonProcessingException {
        String trainingDtoJson=objectMapper.writeValueAsString(trainingDto);
        redisTemplate.opsForValue().set("training:" +trainingDto.getTrainingId(), trainingDtoJson, Duration.ofSeconds(ttl));
    }

    public TrainingDto getTrainingById(String id) throws JsonProcessingException {
        String trainingDtoJson = redisTemplate.opsForValue().get("training:" + id);
        if (trainingDtoJson == null) {
            return null;
        }
        return objectMapper.readValue(trainingDtoJson, TrainingDto.class);
    }
}