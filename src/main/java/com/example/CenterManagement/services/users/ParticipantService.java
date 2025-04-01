package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.exceptions.UserNotFoundException;
import com.example.CenterManagement.mappers.user.ParticipantMapper;
import com.example.CenterManagement.repositories.users.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final UserService userService;
    @Autowired
    public ParticipantService(ParticipantRepository participantRepository,UserService userService) {
        this.participantRepository = participantRepository;
        this.userService = userService;
    }

    public void  createParticipant(ParticipantDto participant) {
       UserDto savedUser=userService.createUser(participant.getUser());
       participant.setUser(savedUser);
        participantRepository.insertParticipant(participant.getUser().getUserId(), participant.getStructure(), participant.getProfile());
    }
    public List<ParticipantDto> getAllParticipants() {
        return participantRepository.findAll().stream().map(ParticipantMapper::toDto).collect(Collectors.toList());
    }
    public ParticipantDto getParticipant(Long id) {
        return ParticipantMapper.toDto(participantRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Participant not found")));
    }
    @Transactional
    public void updateParticipant(ParticipantDto participant) {
        UserDto savedUser = userService.updateUser(participant.getUser());
        participant.setUser(savedUser);
        participantRepository.updateParticipant(
                participant.getUser().getUserId(),
                participant.getStructure(),
                participant.getProfile(),
                participant.getParticipantId()
        );

    }

    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new UserNotFoundException("Participant not found with ID: " + id);
        }
        participantRepository.deleteById(id);
    }

}
