package com.example.CenterManagement.services.training;

import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.entities.training.Training;
import com.example.CenterManagement.entities.user.Trainer;
import com.example.CenterManagement.exceptions.trainings.DomainNotFoundException;
import com.example.CenterManagement.exceptions.trainings.TrainingNotFoundException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.mappers.training.TrainingMapper;
import com.example.CenterManagement.repositories.training.DomainRepository;
import com.example.CenterManagement.repositories.training.TrainingRepository;
import com.example.CenterManagement.repositories.users.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private  final TrainerRepository trainerRepository;
    private final DomainRepository domainRepository;
    @Value("${spring.application.offset}")
    private int offset;
    @Autowired
    TrainingService(TrainingRepository trainingRepository,  TrainerRepository trainerRepository, DomainRepository domainRepository) {
        this.trainingRepository = trainingRepository;
        this.trainerRepository = trainerRepository;
        this.domainRepository = domainRepository;
    }
    public List<TrainingDto> getAllTrainings(int page) {
        return trainingRepository.findAll(PageRequest.of(page,offset)).stream().map(TrainingMapper::toDto).collect(Collectors.toList());
    }
    public TrainingDto getTrainingById(String id) {
        return TrainingMapper.toDto(trainingRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException("Training with id "+id+" not found")));
    }

    @Transactional
    public TrainingDto createTraining(TrainingDto trainingDto, Long trainerId) {
        if(domainRepository.getDomainByDomainName(trainingDto.getDomainName()) == null) {
            throw new DomainNotFoundException("Domain name "+trainingDto.getDomainName()+" does not exist");
        }
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new UserNotFoundException("Trainer not found with id: " + trainerId));
        Training newTraining=Training.builder()
                .domainName(trainingDto.getDomainName())
                .trainingId(trainingDto.getTrainingId())
                .title(trainingDto.getTitle())
                .description(trainingDto.getDescription())
                .startDate(trainingDto.getStartDate())
                .endDate(trainingDto.getEndDate())
                .type(trainingDto.getType())
                .startTime(trainingDto.getStartTime())
                .price(trainingDto.getPrice())
                .endTime(trainingDto.getEndTime())
                .trainer(trainer)
                .build();
        Training savedTraining = trainingRepository.save(newTraining);
        return TrainingMapper.toDto(savedTraining);
    }


    public void deleteTrainingById(String id) {
        if (id == null || !trainingRepository.existsById(id)) {
            throw new TrainingNotFoundException("Training not found with ID: " + id);
        }
        trainingRepository.deleteById(id);
    }
}
