package com.example.CenterManagement.models.requestData;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.TrainerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "Request data object for creating or updating a trainer")
public class TrainerRequestData {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(
            description = "Username of the trainer",
            example = "johndoe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Schema(
            description = "Email address of the trainer",
            example = "johndoe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;



    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Schema(
            description = "Primary phone number of the trainer",
            example = "+1234567890",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;



    @Schema(
            description = "Birth date of the trainer",
            example = "1990-01-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Date dateOfBirth;

    @Schema(
            description = "Gender of the trainer",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Gender gender;

    @Schema(
            description = "Profile picture URL of the trainer",
            example = "https://example.com/profile.jpg",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String profilePicture;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Schema(
            description = "Short description about the trainer",
            example = "A passionate fitness trainer",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotBlank(message = "Trainer type cannot be blank")
    @Schema(
            description = "Type of the trainer",
            example = "INTERNAL",
            allowableValues ={"INTERNAL", "EXTERNAL"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private TrainerType trainerType;

    @NotBlank(message = "Employer name cannot be blank")
    @Schema(
            description = "Name of the employer associated with the trainer",
            example = "FitLife Gym",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long employerId;
}