package com.example.CenterManagement.mappers.user;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.entities.user.Participant;

public class ParticipantMapper {
    public static ParticipantDto toDto(Participant participant){
        return ParticipantDto.builder()
                .participantId(participant.getParticipantId())
                .profile(ProfileMapper.toDto(participant.getProfile()))
                .structure(StructureMapper.toDto(participant.getStructure()))
                .user(UserMapper.toDto(participant.getUser()))
                .build();
    }
    public static ParticipantDto toLightDto(Participant participant){
        return ParticipantDto.builder()
                .participantId(participant.getParticipantId())
                .profile(ProfileMapper.toDto(participant.getProfile()))
                .structure(StructureMapper.toDto(participant.getStructure()))
                .user(UserMapper.toLightDto(participant.getUser()))
                .build();
    }
    public static Participant toEntity(ParticipantDto participantDto){
        return Participant.builder()
                .participantId(participantDto.getParticipantId())
                .profile(ProfileMapper.toEntity(participantDto.getProfile()))
                .structure(StructureMapper.toEntity(participantDto.getStructure()))
                .user(UserMapper.toEntity(participantDto.getUser()))
                .build();
    }
}
