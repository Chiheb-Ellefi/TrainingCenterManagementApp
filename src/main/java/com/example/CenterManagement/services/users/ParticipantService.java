package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.entities.user.Participant;
import com.example.CenterManagement.entities.user.User;
import com.example.CenterManagement.exceptions.users.ProfileNotFoundException;
import com.example.CenterManagement.exceptions.users.StructureNotFoundException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.mappers.user.ParticipantMapper;
import com.example.CenterManagement.mappers.user.UserMapper;
import com.example.CenterManagement.repositories.users.ParticipantRepository;
import com.example.CenterManagement.repositories.users.ProfileRepository;
import com.example.CenterManagement.repositories.users.StructureRepository;
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
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final StructureRepository structureRepository;
    @Value("${spring.application.offset}")
    private int offset;
    @Autowired
    public ParticipantService(ParticipantRepository participantRepository,UserRepository userRepository,ProfileRepository profileRepository,StructureRepository structureRepository) {
        this.participantRepository = participantRepository;
      this.userRepository = userRepository;
      this.profileRepository = profileRepository;
      this.structureRepository = structureRepository;
    }

    public ParticipantDto  createParticipant(ParticipantDto participant) {
if(!profileRepository.existsByProfileType(participant.getProfile())){
    throw new ProfileNotFoundException("Profile type "+ participant.getProfile()+" not supported");
}
if(!structureRepository.existsByStructureName(participant.getStructure())){
    throw new StructureNotFoundException("Structure "+participant.getStructure() +" name not supported");
}
        User user=userRepository.save(UserMapper.toEntity(participant.getUser()));
        Participant newParticipant = Participant.builder()
                .participantId(user.getUserId())
                .user(user)
                .profile(participant.getProfile())
                .structure(participant.getStructure())
                .build();
        return  ParticipantMapper.toDto(participantRepository.save(newParticipant));


    }
    public List<ParticipantDto> getAllParticipants(int page) {
        return participantRepository.findAll(PageRequest.of(page,offset )).stream().map(ParticipantMapper::toLightDto).collect(Collectors.toList());
    }
    public ParticipantDto getParticipant(Long id) {
        return ParticipantMapper.toDto(participantRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Participant not found")));
    }
    @Transactional
    public ParticipantDto updateParticipant(ParticipantDto participant) {
        if(!profileRepository.existsByProfileType(participant.getProfile())){
            throw new ProfileNotFoundException("Profile type "+ participant.getProfile()+" not supported");
        }
        if(!structureRepository.existsByStructureName(participant.getStructure())){
            throw new StructureNotFoundException("Structure "+participant.getStructure() +" name not supported");
        }
        User user = userRepository.save(UserMapper.toEntity(participant.getUser()));
        Participant newParticipant = Participant.builder()
                .participantId(participant.getParticipantId())
                .user(user)
                .profile(participant.getProfile())
                .structure(participant.getStructure())
                .build();

       return  ParticipantMapper.toDto(participantRepository.save(newParticipant));

    }

    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new UserNotFoundException("Participant not found with ID: " + id);
        }
        participantRepository.deleteById(id);
    }

}
