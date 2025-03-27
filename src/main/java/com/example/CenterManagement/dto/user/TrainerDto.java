package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.TrainerType;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrainerDto {

    private Long trainerId;
    private UserDto user;
    private TrainerType trainerType;

}
