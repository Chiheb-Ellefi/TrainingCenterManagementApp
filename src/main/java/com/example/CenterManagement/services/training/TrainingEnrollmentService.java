package com.example.CenterManagement.services.training;

import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.dto.training.TrainingParticipantDto;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.entities.training.Training;
import com.example.CenterManagement.entities.training.TrainingParticipant;
import com.example.CenterManagement.entities.training.TrainingParticipantsId;
import com.example.CenterManagement.entities.user.Participant;
import com.example.CenterManagement.exceptions.UserNotFoundException;
import com.example.CenterManagement.mappers.training.TrainingParticipantMapper;
import com.example.CenterManagement.repositories.training.TrainingParticipantsRepository;
import com.example.CenterManagement.repositories.training.TrainingRepository;
import com.example.CenterManagement.repositories.users.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class TrainingEnrollmentService {
    private final TrainingParticipantsRepository trainingParticipantsRepository;
    private final TrainingRepository trainingRepository;
    private final ParticipantRepository participantRepository;
    @Autowired
    public TrainingEnrollmentService(TrainingParticipantsRepository trainingParticipantsRepository, TrainingRepository trainingRepository, ParticipantRepository participantRepository ) {
        this.trainingParticipantsRepository = trainingParticipantsRepository;
        this.trainingRepository = trainingRepository;
        this.participantRepository = participantRepository;
    }
    public TrainingParticipantDto createTrainingEnrollment(String trainingId,Long userId ) {
        Training training=trainingRepository.findById(trainingId).orElseThrow(()-> new RuntimeException("Training id not found"));
        Participant participant=participantRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User id not found"));
        if (trainingParticipantsRepository.existsById(new TrainingParticipantsId(trainingId,userId)) ){
            throw new IllegalStateException("Participant already enrolled in this training");
        }

        TrainingParticipant enrollment=TrainingParticipant.builder()
                .id(new TrainingParticipantsId(trainingId,userId))
                .training(training)
                .participant(participant)
                .build();

        return TrainingParticipantMapper.toDto(trainingParticipantsRepository.save(enrollment));
    }
    public List<ParticipantDto> getEnrollmentParticipants(String trainingId) {
        return trainingParticipantsRepository.findAllByTrainingId(trainingId).stream().map((enrollment)->
            TrainingParticipantMapper.toDto(enrollment).getParticipant()
        ).collect(Collectors.toList());
    }
    public List<TrainingDto> getParticipantsEnrollment(Long user_id) {
        return trainingParticipantsRepository.findTrainingsForParticipant(user_id).stream().map((enrollment)->
                TrainingParticipantMapper.toDto(enrollment).getTraining()
        ).collect(Collectors.toList());
    }


    public void cancelTrainingEnrollment(Long user_id,String id) {
        if (!trainingParticipantsRepository.existsById(new TrainingParticipantsId(id,user_id))) {
            throw new IllegalStateException("Enrollment doesnt exist");
        }
        this.trainingParticipantsRepository.deleteById(new TrainingParticipantsId(id,user_id));
    }

}
