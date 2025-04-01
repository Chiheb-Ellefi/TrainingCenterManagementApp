package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.mappers.user.TrainerMapper;
import com.example.CenterManagement.repositories.users.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    @Autowired
    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository =  trainerRepository;
    }
    public List<TrainerDto> getAllTrainers() {
        return trainerRepository.findAll().stream().map(TrainerMapper::toDto).collect(Collectors.toList());
    }

    public TrainerDto getTrainer(Long id) {
        return TrainerMapper.toDto(trainerRepository.findById(id).orElseThrow(()-> new RuntimeException("Trainer not found")));
    }
    public TrainerDto createTrainer(TrainerDto trainerDto) {
       return  TrainerMapper.toDto(trainerRepository.save(TrainerMapper.toEntity(trainerDto)));
    }
    public TrainerDto updateTrainer( TrainerDto trainerDto) {
        if(trainerDto.getTrainerId() == null || !trainerRepository.existsById(trainerDto.getTrainerId())) {
            throw new RuntimeException("Trainer id not found");
        }
        return TrainerMapper.toDto(trainerRepository.save(TrainerMapper.toEntity(trainerDto)));
    }
    public void deleteTrainer(Long id) {
        if(id== null ) {
            throw new RuntimeException("Trainer id not found");
        }
        trainerRepository.deleteById(id);
    }
}
