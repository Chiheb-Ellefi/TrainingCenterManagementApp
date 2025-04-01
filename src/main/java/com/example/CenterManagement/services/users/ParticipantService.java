package com.example.CenterManagement.services.users;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.mappers.user.ParticipantMapper;
import com.example.CenterManagement.repositories.users.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    @Autowired
    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public ParticipantDto createParticipant(ParticipantDto participant) {
        return ParticipantMapper.toDto(participantRepository.save(ParticipantMapper.toEntity(participant)));
    }
    public List<ParticipantDto> getAllParticipants() {
        return participantRepository.findAll().stream().map(ParticipantMapper::toDto).collect(Collectors.toList());
    }
    public ParticipantDto getParticipant(Long id) {
        return ParticipantMapper.toDto(participantRepository.findById(id).orElseThrow(() -> new RuntimeException("Participant not found")));
    }
    @Transactional
    public ParticipantDto updateParticipant( ParticipantDto participant) {
        if(participant == null || participant.getParticipantId() == null || !participantRepository.existsById(participant.getParticipantId())) {
            throw new RuntimeException("Participant not found with ID: " + participant.getParticipantId());
        }
        return ParticipantMapper.toDto(participantRepository.save(ParticipantMapper.toEntity(participant)));
    }
    @Transactional
    public void deleteParticipant(Long id) {
        if (id == null || !participantRepository.existsById(id)) {
            throw new RuntimeException("Participant not found with ID: " + id);
        }
        participantRepository.deleteById(id);
    }

}
