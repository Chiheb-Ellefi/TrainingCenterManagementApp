package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.TrainerType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerDto {
    private Long trainerId;
    @JsonIgnoreProperties({"userId","password","role","isVerified","phoneNumber","secondPhoneNumber","dateOfBirth","description","gender"})
    private UserDto user;
    private TrainerType trainerType;
    private String employerName;

}
