package com.example.CenterManagement.services.training;

import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.mappers.training.TrainingMapper;
import com.example.CenterManagement.repositories.training.TrainingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
//add the training not found exception
@Service
public class TrainingService {
    private final TrainingRepository trainingRepository;

    @Autowired
    TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream().map(TrainingMapper::toDto).collect(Collectors.toList());
    }
    public TrainingDto getTrainingById(String id) {
        return TrainingMapper.toDto(trainingRepository.findById(id).orElseThrow(() -> new RuntimeException("Training not found")));
    }
    public TrainingDto createTraining(TrainingDto trainingDto) {
        return TrainingMapper.toDto(trainingRepository.save(TrainingMapper.toEntity(trainingDto)));
    }
    @Transactional
    public TrainingDto updateTrainingById( TrainingDto trainingDto) {
        if(trainingDto.getTrainingId() == null || trainingRepository.existsById(trainingDto.getTrainingId())) {
            throw new RuntimeException("Training id not found");
        }
        return TrainingMapper.toDto(trainingRepository.save(TrainingMapper.toEntity(trainingDto)));
    }

    public void deleteTrainingById(String id) {
        if (id == null || !trainingRepository.existsById(id)) {
            throw new RuntimeException("Training not found with ID: " + id);
        }
        trainingRepository.deleteById(id);
    }
}
