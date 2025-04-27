package com.example.CenterManagement.mappers.training;

import com.example.CenterManagement.dto.training.TrainingParticipantDto;
import com.example.CenterManagement.entities.training.TrainingParticipant;
import com.example.CenterManagement.mappers.user.UserMapper;

public class TrainingParticipantMapper {
  public static   TrainingParticipant toEntity(TrainingParticipantDto trainingParticipantDto) {
      return TrainingParticipant.builder()
              .participant(UserMapper.toEntity(trainingParticipantDto.getParticipant()))
              .training(TrainingMapper.toEntity(trainingParticipantDto.getTraining()))
              .build();
  }
  public static TrainingParticipantDto toDto(TrainingParticipant trainingParticipant) {
      return TrainingParticipantDto.builder()
              .training(TrainingMapper.toDto(trainingParticipant.getTraining()))
              .participant(UserMapper.toLightDto(trainingParticipant.getParticipant()))
              .build();
  }

}
