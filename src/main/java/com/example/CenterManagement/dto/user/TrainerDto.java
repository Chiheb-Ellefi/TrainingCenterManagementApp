package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.TrainerType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrainerDto {

    private Long trainerId;
    @JsonIgnoreProperties({"password","role","isVerified","phoneNumber","secondPhoneNumber","dateOfBirth","description","gender"})
    private UserDto user;
    private TrainerType trainerType;
    private String employerName;

}
