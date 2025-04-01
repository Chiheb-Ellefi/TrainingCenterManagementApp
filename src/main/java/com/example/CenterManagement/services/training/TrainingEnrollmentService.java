package com.example.CenterManagement.services.training;

import com.example.CenterManagement.dto.training.TrainingParticipantDto;
import com.example.CenterManagement.mappers.training.TrainingParticipantMapper;
import com.example.CenterManagement.repositories.training.TrainingParticipantsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingEnrollmentService {
    private final TrainingParticipantsRepository trainingParticipantsRepository;
    @Autowired
    public TrainingEnrollmentService(TrainingParticipantsRepository trainingParticipantsRepository ){
        this.trainingParticipantsRepository = trainingParticipantsRepository;
    }
    public TrainingParticipantDto createTrainingEnrollment(TrainingParticipantDto trainingParticipantDto) {
        return TrainingParticipantMapper.toDto(trainingParticipantsRepository.save(TrainingParticipantMapper.toEntity(trainingParticipantDto)));
    }
    public TrainingParticipantDto getTrainingEnrollment(Long enrollmentId) {
        if(enrollmentId == null) {
            throw new IllegalArgumentException("Enrollment id cannot be null");
        }
        return TrainingParticipantMapper.toDto(trainingParticipantsRepository.findById(enrollmentId).orElseThrow(()->new RuntimeException("Training Enrollment Not Found")));
    }
    @Transactional
    public void cancelTrainingEnrollment(Long enrollmentId) {
        if(enrollmentId==null || !trainingParticipantsRepository.existsById(enrollmentId)) {
            throw new RuntimeException("Training Enrollment Not Found");
        }
        this.trainingParticipantsRepository.deleteById(enrollmentId);
    }

}
