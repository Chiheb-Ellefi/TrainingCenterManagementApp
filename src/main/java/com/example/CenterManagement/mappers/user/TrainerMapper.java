package com.example.CenterManagement.mappers.user;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.entities.user.Trainer;

public class TrainerMapper {
    public static TrainerDto toDto(Trainer trainer) {
        return TrainerDto.builder()
                .trainerId(trainer.getTrainerId())
                .user(UserMapper.toDto(trainer.getUser()))
                .trainerType(trainer.getTrainerType())
                .employerName(trainer.getEmployerName())
                .build();
    }
    public static Trainer toEntity(TrainerDto trainerDto) {
        return Trainer.builder()
                .trainerId(trainerDto.getTrainerId())
                .user(UserMapper.toEntity(trainerDto.getUser()))
                .trainerType(trainerDto.getTrainerType())
                .employerName(trainerDto.getEmployerName())
                .build();
    }
}
