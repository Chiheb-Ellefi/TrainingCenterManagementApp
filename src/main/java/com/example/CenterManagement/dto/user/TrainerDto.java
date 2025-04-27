package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.TrainerType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data transfer object containing trainer details")
public class TrainerDto {

    @Schema(
            description = "Auto-generated unique identifier of the trainer",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long trainerId;

    @Schema(
            description = "Contains user details including name, contact information, and profile data",
            implementation = UserDto.class,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UserDto user;

    @NotNull(message = "Trainer type cannot be null")
    @Schema(
            description = "Classification of trainer based on their specialization",
            example = "PERSONAL",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"PERSONAL", "GROUP", "VIRTUAL"},
            implementation = TrainerType.class
    )
    private TrainerType trainerType;

    @NotNull(message = "Employer information cannot be null")
    @Schema(
            description = "Organization details that employs this trainer",
            implementation = EmployerDto.class,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private EmployerDto employer;
}