package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.Role;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.ParticipantRequestData;
import com.example.CenterManagement.services.users.ParticipantService;
import com.example.CenterManagement.utils.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController {
    private final ParticipantService participantService;
    @Autowired
   public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;

    }
@GetMapping
    public ResponseEntity<List<ParticipantDto>> getAllParticipants(@RequestParam(required = false,defaultValue = "0") Integer page) {
        List<ParticipantDto> participants = participantService.getAllParticipants(page);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> getParticipant(@PathVariable Long id) {
        if(id==null){
            throw new BadRequestException("Provided participant id is null");
        }
        ParticipantDto participant = participantService.getParticipant(id);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantRequestData data) {
        if(data==null || data.getEmail()==null || data.getUsername()==null){
            throw new BadRequestException("Provided participant data is null or empty");
        }
        String password= RandomPasswordGenerator.generateRandomPassword();
        UserDto user = UserDto.builder()
                .email(data.getEmail())
                .username(data.getUsername())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .secondPhoneNumber(data.getSecondPhoneNumber())
                .role(Role.PARTICIPANT)
                .password(password)
                .isVerified(false)
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();
       ParticipantDto participantDto=ParticipantDto.builder()
               .user(user)
               .structure(data.getStructure())
               .profile(data.getProfile())
               .build();
       ParticipantDto participant=participantService.createParticipant(participantDto);
       return new ResponseEntity<>(participant, HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<ParticipantDto> updateParticipant(@PathVariable Long id, @RequestBody ParticipantRequestData data) {

        if(id==null ){
            throw new BadRequestException("Provided participantId is null ");
        }
        if(data==null ){
            throw new BadRequestException("Provided participant data is null ");
        }
        ParticipantDto participant = participantService.getParticipant(id);
        if(participant==null){
            throw new UserNotFoundException("User not found");
        }
        UserDto oldUser = participant.getUser();
        UserDto newUser=UserDto.builder()
                .username(data.getUsername() != null ? data.getUsername() : oldUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : oldUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : oldUser.getDescription())
                .password(oldUser.getPassword())
                .role(Role.PARTICIPANT)
                .userId(participant.getUser().getUserId())
                .gender(data.getGender() != null ? data.getGender() : oldUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : oldUser.getPhoneNumber())
                .secondPhoneNumber(data.getSecondPhoneNumber() != null ? data.getSecondPhoneNumber() : oldUser.getSecondPhoneNumber())
                .isVerified(data.getIsVerified() != null ? data.getIsVerified() : oldUser.getIsVerified())
                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        ParticipantDto newParticipant=ParticipantDto.builder()
                .participantId(participant.getParticipantId())
                .user(newUser)
                .structure(data.getStructure()!=null?data.getStructure():participant.getStructure())
                .profile(data.getProfile()!=null?data.getProfile():participant.getProfile())
                .build();
       ParticipantDto participantDto= participantService.updateParticipant(newParticipant);
        return new ResponseEntity<>(participantDto, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long id) {
        if(id==null){
            throw new BadRequestException("Provided participant id is null ");
        }
        participantService.deleteParticipant(id);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
