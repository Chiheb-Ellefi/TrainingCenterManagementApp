package com.example.CenterManagement.models.requestData;

import com.example.CenterManagement.entities.user.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Date;

@Getter
@Schema(description = "Request data object for creating or updating a participant")
public class ParticipantRequestData {

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Schema(
            description = "Username of the participant",
            example = "johndoe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Schema(
            description = "Email address of the participant",
            example = "johndoe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;


    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Schema(
            description = "Primary phone number of the participant",
            example = "+1234567890",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String phoneNumber;



    @Schema(
            description = "Birth date of the participant",
            example = "1990-01-01",
            type = "string",
            format = "date",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Date dateOfBirth;

    @Schema(
            description = "Gender of the participant",
            example = "MALE",
            allowableValues = {"MALE", "FEMALE"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Gender gender;

    @Schema(
            description = "Profile picture URL of the participant",
            example = "https://example.com/profile.jpg",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String profilePicture;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Schema(
            description = "Short description about the participant",
            example = "A passionate participant in fitness programs",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String description;

    @NotBlank(message = "Structure name cannot be blank")
    @Size(min = 1, max = 100, message = "Structure name must be between 1 and 100 characters")
    @Schema(
            description = "Name of the structure associated with the participant",
            example = "FitLife Gym",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long structure;

    @NotBlank(message = "Profile cannot be blank")
    @Size(min = 1, max = 100, message = "Profile must be between 1 and 100 characters")
    @Schema(
            description = "Profile of the participant",
            example = "Fitness Enthusiast",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long profile;
}