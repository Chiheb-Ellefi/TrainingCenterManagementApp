package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.TrainerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Schema(description = "Data transfer object for trainer")
public class TrainerDto {

    @Schema(
            description = "Unique identifier of the trainer (autogenerated)",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long trainerId;


    @Schema(
            description = "Embedded user information (excluding sensitive fields)",
            implementation = UserDto.class
    )
    private UserDto user;

    @NotNull(message = "Trainer type cannot be null")
    @Schema(
            description = "Type of the trainer (e.g., PERSONAL, GROUP)",
            example = "PERSONAL",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"PERSONAL", "GROUP"}
    )
    private TrainerType trainerType;

    @NotBlank(message = "Employer name cannot be blank")
    @Schema(
            description = "Name of the employer associated with the trainer",
            example = "FitLife Gym",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String employerName;
}