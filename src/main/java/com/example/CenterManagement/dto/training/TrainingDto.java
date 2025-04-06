package com.example.CenterManagement.dto.training;

import com.example.CenterManagement.dto.user.TrainerDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Data transfer object representing a training")
public class TrainingDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(
            description = "Unique identifier of the training (autogenerated as UUID)",
            example = "550e8400-e29b-41d4-a716-446655440000",
            accessMode = Schema.AccessMode.READ_ONLY // Indicates this field is read-only and autogenerated
    )
    private String trainingId;

    @NotBlank(message = "Training title cannot be blank")
    @Schema(
            description = "Title of the training",
            example = "Strength Training Workshop",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(
            description = "Start date of the training",
            example = "2023-10-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date startDate;

    @NotNull(message = "End date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(
            description = "End date of the training",
            example = "2023-10-05",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date endDate;

    @Schema(
            description = "Description of the training",
            example = "A comprehensive workshop on strength training techniques",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotBlank(message = "Domain name cannot be blank")
    @Schema(
            description = "Name of the domain associated with the training",
            example = "Fitness",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String domainName;

    @JsonIgnoreProperties({"userId", "password", "role", "isVerified", "phoneNumber", "secondPhoneNumber", "dateOfBirth", "description", "gender"})
    @Schema(
            description = "Trainer responsible for the training",
            implementation = TrainerDto.class,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TrainerDto trainer;
}