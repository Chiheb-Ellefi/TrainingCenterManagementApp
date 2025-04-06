package com.example.CenterManagement.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "Request data object for creating or updating a training")
public class TrainingRequestData {

    @NotBlank(message = "Title cannot be blank")
    @Schema(
            description = "Title of the training",
            example = "Strength Training Workshop",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @NotNull(message = "Start date cannot be null")
    @Schema(
            description = "Start date of the training",
            example = "2023-10-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Date startDate;

    @NotNull(message = "End date cannot be null")
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

    @NotNull(message = "Trainer ID cannot be null")
    @Schema(
            description = "ID of the trainer responsible for the training",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long trainerId;
}