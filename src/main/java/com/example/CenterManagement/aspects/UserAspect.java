package com.example.CenterManagement.aspects;

import com.example.CenterManagement.annotations.users.CheckInCache;
import com.example.CenterManagement.annotations.users.UpdateUserInCache;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.repositories.users.redisRepositories.ParticipantCacheRepository;
import com.example.CenterManagement.repositories.users.redisRepositories.TrainerCacheRepository;
import com.example.CenterManagement.repositories.users.redisRepositories.UserCacheRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {
    private final UserCacheRepository userCacheRepository;
private final ParticipantCacheRepository participantRepository;
private final TrainerCacheRepository trainerRepository;

    public UserAspect(UserCacheRepository userCacheRepository, ParticipantCacheRepository participantRepository, TrainerCacheRepository trainerRepository) {
        this.userCacheRepository = userCacheRepository;
        this.participantRepository = participantRepository;
        this.trainerRepository = trainerRepository;
    }

    @Around("@annotation(checkInCache)")
    public Object checkUserInCache(ProceedingJoinPoint joinPoint, CheckInCache checkInCache) throws Throwable {
        Long userId = extractUserId(joinPoint.getArgs());
        if (userId == null) {
            return joinPoint.proceed();
        }
        String type=checkInCache.type();
        Object cachedUser = switch (type) {
            case "user" -> userCacheRepository.getUserById(userId.toString());
            case "participant" -> participantRepository.getParticipantFromCache(userId.toString());
            case "trainer" -> trainerRepository.getTrainerById(userId.toString());
            default -> null;
        };

        if (cachedUser != null) {
            return ResponseEntity.ok(cachedUser);
        }

        ResponseEntity<?> response = (ResponseEntity<?>) joinPoint.proceed();

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            if (response.getBody() instanceof UserDto) {
                userCacheRepository.addUserToCache((UserDto) response.getBody());
            }
            if(response.getBody() instanceof ParticipantDto){
                participantRepository.addParticipantToCache((ParticipantDto) response.getBody());
            }
            if(response.getBody() instanceof TrainerDto){
                trainerRepository.addTrainerToCache((TrainerDto) response.getBody());
            }
        }
        return response;
    }

    @Around("@annotation(updateUserInCache)")
    public Object updateUserCache(ProceedingJoinPoint joinPoint, UpdateUserInCache updateUserInCache) throws Throwable {
       ResponseEntity<?> response=(ResponseEntity<?>) joinPoint.proceed();
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            if (response.getBody() instanceof UserDto) {
                userCacheRepository.addUserToCache((UserDto) response.getBody());
            }
            if(response.getBody() instanceof ParticipantDto){
                participantRepository.addParticipantToCache((ParticipantDto) response.getBody());
            }
            if(response.getBody() instanceof TrainerDto){
                trainerRepository.addTrainerToCache((TrainerDto) response.getBody());
            }
        }
        return response;
    }

    private Long extractUserId(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }

        Object arg = args[0];
        if (arg instanceof Long) {
            return (Long) arg;
        } else if (arg instanceof String) {
            try {
                return Long.parseLong((String) arg);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}