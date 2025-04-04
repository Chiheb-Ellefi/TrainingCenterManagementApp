package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.Role;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.UserRequestData;
import com.example.CenterManagement.services.training.TrainingEnrollmentService;
import com.example.CenterManagement.services.users.ParticipantService;
import com.example.CenterManagement.services.users.TrainerService;
import com.example.CenterManagement.services.users.UserService;
import com.example.CenterManagement.utils.EnumsHelperMethods;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final ParticipantService participantService;
    private final TrainerService trainerService;
    private final TrainingEnrollmentService trainingEnrollmentService;
    @Autowired
    public UserController(UserService userService, ParticipantService participantService, TrainerService trainerService, TrainingEnrollmentService trainingEnrollmentService) {
        this.userService = userService;
        this.participantService = participantService;
        this.trainerService = trainerService;
        this.trainingEnrollmentService = trainingEnrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") Integer page) {
       List<UserDto> response= userService.getAllUsers(page);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
      UserDto response=  userService.getUserById(userId);
      return ResponseEntity.ok().body(response);
    }
    @PostMapping
    public ResponseEntity<Object> createUser (@RequestBody UserRequestData data) throws BadRequestException {
        boolean wrongRequest=data==null || data.getUsername()==null || data.getEmail()==null || !EnumsHelperMethods.isValidRole(data.getRole());
        if(wrongRequest){
            throw new BadRequestException("Invalid input data, please try again");
        }
        UserDto userDto=UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .password(data.getPassword())
                .role(data.getRole())
                .description(data.getDescription())
                .isVerified(false)
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .secondPhoneNumber(data.getSecondPhoneNumber())
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();
        if(userDto.getRole()== Role.PARTICIPANT){
            ParticipantDto participantDto=ParticipantDto.builder()
                    .user(userDto)
                    .structure(data.getStructure())
                    .profile(data.getProfile())
                    .build();
           ParticipantDto participant= participantService.createParticipant(participantDto);
            return new ResponseEntity<>(participant, HttpStatus.CREATED);
        }
        if(userDto.getRole()== Role.TRAINER){
            TrainerDto trainerDto=TrainerDto.builder()
                    .user(userDto)
                    .employerName(data.getEmployerName())
                    .trainerType(data.getTrainerType())
                    .build();
           TrainerDto trainer= trainerService.createTrainer(trainerDto);
            return new ResponseEntity<>(trainer, HttpStatus.CREATED);
        }

        UserDto response=userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Long userId) throws BadRequestException {
        if(userId==null){
            throw new BadRequestException("Invalid user Id, please try again");
        }
        UserDto oldUser=userService.getUserById(userId);
        if(oldUser==null){
            throw new UserNotFoundException("No user found with id "+userId);
        }
        UserDto updatedUser = UserDto.builder()
                .username(userDto.getUsername() != null ? userDto.getUsername() : oldUser.getUsername())
                .email(userDto.getEmail() != null ? userDto.getEmail() : oldUser.getEmail())
                .password(userDto.getPassword() != null ? userDto.getPassword() : oldUser.getPassword())
                .role(userDto.getRole() != null ? userDto.getRole() : oldUser.getRole())
                .dateOfBirth(userDto.getDateOfBirth() != null ? userDto.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(userDto.getDescription() != null ? userDto.getDescription() : oldUser.getDescription())
                .userId(userId)
                .gender(userDto.getGender() != null ? userDto.getGender() : oldUser.getGender())
                .phoneNumber(userDto.getPhoneNumber() != null ? userDto.getPhoneNumber() : oldUser.getPhoneNumber())
                .secondPhoneNumber(userDto.getSecondPhoneNumber() != null ? userDto.getSecondPhoneNumber() : oldUser.getSecondPhoneNumber())
                .isVerified(userDto.getIsVerified() != null ? userDto.getIsVerified() : oldUser.getIsVerified())
                .profilePicture(userDto.getProfilePicture() != null ? userDto.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        UserDto response=userService.updateUser(updatedUser);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws BadRequestException {
       if(userId==null){
           throw new BadRequestException("The provided user id is null");
       }
       userService.deleteUser(userId);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/{id}/enrollments")
    public ResponseEntity<List<TrainingDto>> getEnrollments(@PathVariable Long id) throws BadRequestException {
        if(id==null){
            throw new BadRequestException("The provided user id is null");
        }
        List<TrainingDto> enrollments=trainingEnrollmentService.getParticipantsEnrollment(id);
        return ResponseEntity.ok(enrollments);
    }

}
