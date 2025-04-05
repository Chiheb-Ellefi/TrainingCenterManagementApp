package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.Trainer;
import com.example.CenterManagement.entities.user.User;
import com.example.CenterManagement.exceptions.users.EmployerNotFoundException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.mappers.user.TrainerMapper;
import com.example.CenterManagement.mappers.user.UserMapper;
import com.example.CenterManagement.repositories.users.EmployerRepository;
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
    private final EmployerRepository employerRepository;
    @Value("${spring.application.offset}")
    private int offset;
    @Autowired
    public TrainerService(TrainerRepository trainerRepository, UserRepository userRepository, EmployerRepository employerRepository) {
        this.trainerRepository =  trainerRepository;
        this.userRepository = userRepository;
        this.employerRepository = employerRepository;
    }
    public List<TrainerDto> getAllTrainers(int page) {
        return trainerRepository.findAll(PageRequest.of(page,offset)).stream().map(TrainerMapper::toDto).collect(Collectors.toList());
    }

    public TrainerDto getTrainer(Long id) {
        return TrainerMapper.toDto(trainerRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Trainer not found")));
    }
    @Transactional
    public TrainerDto  createTrainer(TrainerDto trainerDto) {
        if(!employerRepository.existsBYEmployerName(trainerDto.getEmployerName())){
            throw new EmployerNotFoundException("Employer with name: "+trainerDto.getEmployerName()+" does not exist");
        }
        User savedUser=userRepository.save(UserMapper.toEntity(trainerDto.getUser()));
        Trainer trainer=Trainer.builder()
                .trainerId(savedUser.getUserId())
                .user(savedUser)
                .trainerType(trainerDto.getTrainerType())
                .employerName(trainerDto.getEmployerName())
                .build();
       Trainer savedTrainer= trainerRepository.save(trainer);
       return TrainerMapper.toDto(savedTrainer);
    }

    public TrainerDto updateTrainer( TrainerDto trainerDto, UserDto userDto) {
        if( !trainerRepository.existsById(trainerDto.getTrainerId())) {
            throw new UserNotFoundException("Trainer id not found");
        }
        if(!employerRepository.existsBYEmployerName(trainerDto.getEmployerName())){
            throw new EmployerNotFoundException("Employer with name: "+trainerDto.getEmployerName()+" does not exist");
        }
        User savedUser=userRepository.save(UserMapper.toEntity(userDto));
        Trainer trainer=Trainer.builder()
                .trainerId(trainerDto.getTrainerId())
                .user(savedUser)
                .trainerType(trainerDto.getTrainerType())
                .employerName(trainerDto.getEmployerName())
                .build();
        Trainer savedTrainer= trainerRepository.save(trainer);
        return TrainerMapper.toDto(savedTrainer);
    }
    public void deleteTrainer(Long id) {
        if(!trainerRepository.existsById(id) ) {
            throw new UserNotFoundException("Trainer id not found");
        }
        trainerRepository.deleteById(id);
    }
}
