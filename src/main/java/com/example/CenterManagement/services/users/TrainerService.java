package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.exceptions.UserNotFoundException;
import com.example.CenterManagement.mappers.user.TrainerMapper;
import com.example.CenterManagement.mappers.user.UserMapper;
import com.example.CenterManagement.repositories.users.TrainerRepository;
import com.example.CenterManagement.repositories.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    @Value("${spring.application.offset}")
    private int offset;
    @Autowired
    public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository) {
        this.trainerRepository =  trainerRepository;
        this.userRepository = userRepository;
    }
    public List<TrainerDto> getAllTrainers(int page) {
        return trainerRepository.findAll(PageRequest.of(page,offset)).stream().map(TrainerMapper::toDto).collect(Collectors.toList());
    }

    public TrainerDto getTrainer(Long id) {
        return TrainerMapper.toDto(trainerRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Trainer not found")));
    }
    public void  createTrainer(TrainerDto trainerDto) {
        UserDto savedUser=UserMapper.toDto(userRepository.save(UserMapper.toEntity(trainerDto.getUser())));
        if(savedUser==null){
            throw new RuntimeException("Cant save User, try again later");
        }
        trainerRepository.createTrainer(savedUser.getUserId(), trainerDto.getTrainerType().name(),trainerDto.getEmployerName());
    }

    public void updateTrainer( TrainerDto trainerDto, UserDto userDto) {
        if( !trainerRepository.existsById(trainerDto.getTrainerId())) {
            throw new UserNotFoundException("Trainer id not found");
        }
        UserDto savedUser=UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
        if(savedUser==null){
            throw new RuntimeException("Cant save User, try again later");
        }
        trainerRepository.updateTrainer(savedUser.getUserId(),trainerDto.getTrainerType().name(),trainerDto.getEmployerName(),trainerDto.getTrainerId());
    }
    public void deleteTrainer(Long id) {
        if(!trainerRepository.existsById(id) ) {
            throw new UserNotFoundException("Trainer id not found");
        }

        trainerRepository.deleteById(id);
    }
}
