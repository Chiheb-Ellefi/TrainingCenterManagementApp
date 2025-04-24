package com.example.CenterManagement.aspects;

import com.example.CenterManagement.annotations.training.UpdateTrainingInCache;
import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.repositories.training.TrainingCacheRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrainingAspect {
    private final TrainingCacheRepository trainingCacheRepository;
    @Autowired
    TrainingAspect(TrainingCacheRepository trainingCacheRepository) {
        this.trainingCacheRepository = trainingCacheRepository;
    }

    @Around("@annotation(com.example.CenterManagement.annotations.training.CheckInCache)")
    public Object checkTrainingInCache(ProceedingJoinPoint joinPoint) throws Throwable {
        if(extractTrainingId(joinPoint.getArgs()) == null){
           return  joinPoint.proceed();
        }
       TrainingDto trainingDto= trainingCacheRepository.getTrainingById(extractTrainingId(joinPoint.getArgs()));
        if(trainingDto != null){
            return new ResponseEntity<>(trainingDto, HttpStatus.OK);
        }
       ResponseEntity<?> response=(ResponseEntity<?>) joinPoint.proceed();
        if(response.getStatusCode().equals(HttpStatus.OK )&& response.getBody() instanceof TrainingDto){
            trainingCacheRepository.addTrainingToCache((TrainingDto) response.getBody());
        }
        return response;
    }


    private String extractTrainingId(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }

        Object arg = args[0];
        if (arg instanceof String) {
            return (String) arg;
        }
        return null;
    }
    @Around("@annotation(updateTrainingInCache)")
    public Object updateTrainingInCache(ProceedingJoinPoint joinPoint, UpdateTrainingInCache updateTrainingInCache) throws Throwable {
        ResponseEntity<?> response=(ResponseEntity<?>) joinPoint.proceed();
        if(response.getStatusCode().equals(HttpStatus.OK )&& response.getBody() instanceof TrainingDto){
            trainingCacheRepository.addTrainingToCache((TrainingDto) response.getBody());
        }
        return response;
    }
}
