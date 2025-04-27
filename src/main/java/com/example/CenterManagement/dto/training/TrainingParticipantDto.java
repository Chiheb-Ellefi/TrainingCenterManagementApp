package com.example.CenterManagement.dto.training;

import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object representing a training participant relationship")
public class TrainingParticipantDto {

    @Schema(
            description = "The training associated with the participant",
            implementation = TrainingDto.class,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TrainingDto training;

    @Schema(
            description = "The user enrolled in the training",
            implementation = UserDto.class,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UserDto participant;
}