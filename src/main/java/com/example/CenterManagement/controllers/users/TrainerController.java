package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.Role;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.TrainerRequestData;
import com.example.CenterManagement.services.users.TrainerService;
import com.example.CenterManagement.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
public class TrainerController {
    private final TrainerService trainerService;
    @Autowired
    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }
    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers(@RequestParam(required = false ,defaultValue = "0")Integer page) {
        List<TrainerDto> trainers = trainerService.getAllTrainers(page);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) throws UserNotFoundException {
        if(id == null) {
            throw new BadRequestException("Trainer id cannot be null");
        }
        TrainerDto trainer = trainerService.getTrainer(id);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerRequestData data) {
        if(data==null){
            throw new BadRequestException("The provided trainer is null");
        }
        String password= RandomPasswordGenerator.generateRandomPassword();
        UserDto userDto = UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .role(Role.TRAINER)
                .phoneNumber(data.getPhoneNumber())
                .profilePicture(data.getProfilePicture())
                .secondPhoneNumber(data.getSecondPhoneNumber())
                .isVerified(false)
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .gender(data.getGender())
                .password(password)
                .build();
        TrainerDto trainerDto=TrainerDto.builder()
                .user(userDto)
                .trainerType(data.getTrainerType())
                .employerName(data.getEmployerName())
                .build();
      TrainerDto trainer=trainerService.createTrainer(trainerDto);
        return new ResponseEntity<>(trainer, HttpStatus.CREATED);

    }
    @PatchMapping("/{id}")
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable Long id, @RequestBody TrainerRequestData data) {
        if(data==null){
            throw new BadRequestException("The provided trainer is null");
        }
        if(id==null){
            throw new BadRequestException("The provided trainer id is null");
        }
        TrainerDto oldTrainer=trainerService.getTrainer(id);
        if(oldTrainer==null){
            throw new UserNotFoundException("Trainer not found");
        }
        UserDto oldUser=oldTrainer.getUser();
        UserDto newUser=UserDto.builder()
                .username(data.getUsername() != null ? data.getUsername() : oldUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : oldUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : oldUser.getDescription())
                .password(oldUser.getPassword())
                .role(Role.TRAINER)
                .userId(oldUser.getUserId())
                .gender(data.getGender() != null ? data.getGender() : oldUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : oldUser.getPhoneNumber())
                .secondPhoneNumber(data.getSecondPhoneNumber() != null ? data.getSecondPhoneNumber() : oldUser.getSecondPhoneNumber())
                .isVerified(data.getIsVerified() != null ? data.getIsVerified() : oldUser.getIsVerified())
                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        TrainerDto newTrainer=TrainerDto.builder()
                .trainerId(oldTrainer.getTrainerId())
                .user(oldUser)
                .trainerType(data.getTrainerType()!=null ? data.getTrainerType() : oldTrainer.getTrainerType())
                .employerName(data.getEmployerName()!=null ? data.getEmployerName() : oldTrainer.getEmployerName())
                .build();
      TrainerDto trainer=  trainerService.updateTrainer(newTrainer,newUser);
        return  new ResponseEntity<>(trainer, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Long id) throws UserNotFoundException {
        if(id==null){
            throw new BadRequestException("Trainer id cannot be null");
        }
        trainerService.deleteTrainer(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
