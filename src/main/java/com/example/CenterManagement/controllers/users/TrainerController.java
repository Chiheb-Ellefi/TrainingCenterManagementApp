package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.annotations.users.CheckInCache;
import com.example.CenterManagement.annotations.users.UpdateUserInCache;
import com.example.CenterManagement.dto.user.TrainerDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.Role;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.requestData.TrainerRequestData;
import com.example.CenterManagement.services.users.EmailService;
import com.example.CenterManagement.services.users.TrainerService;
import com.example.CenterManagement.utils.RandomPasswordGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/trainers")
public class TrainerController {

    private final TrainerService trainerService;
    private final EmailService emailService;
    @Autowired
    public TrainerController(TrainerService trainerService,EmailService emailService) {
        this.trainerService = trainerService;
        this.emailService = emailService;
    }

    @Operation(
            summary = "Get paginated list of all trainers",
            description = "Retrieves a list of trainers based on the provided page number. Default is page 0.",
            parameters = {
                    @Parameter(name = "page", description = "Page number for pagination")
            }
    )
    @ApiResponse(responseCode = "200", description = "List of trainers successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TrainerDto.class))))
    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers(@RequestParam(required = false, defaultValue = "0") Integer page) {
        List<TrainerDto> trainers = trainerService.getAllTrainers(page);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a trainer by ID",
            description = "Fetches a trainer using their unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Trainer found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainerDto.class))),
            @ApiResponse(responseCode = "400", description = "Provided trainer ID is null", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content(mediaType = "text/plain"))
    })
    @CheckInCache
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) throws UserNotFoundException {
        if (id == null) {
            throw new BadRequestException("Trainer id cannot be null");
        }

        TrainerDto trainer = trainerService.getTrainer(id);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new trainer",
            description = "Creates a new trainer with the provided data. A random password is generated for the trainer.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new trainer",
                    content = @Content(schema = @Schema(implementation = TrainerRequestData.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Trainer created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerRequestData data) {
        if (data == null) {
            throw new BadRequestException("The provided trainer is null");
        }
        String password = RandomPasswordGenerator.generateRandomPassword();
        UserDto userDto = UserDto.builder()
                .username(data.getUsername())
                .email(data.getEmail())
                .role(Role.TRAINER)
                .phoneNumber(data.getPhoneNumber())
                .profilePicture(data.getProfilePicture())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .gender(data.getGender())
                .password(password)
                .build();
        TrainerDto trainerDto = TrainerDto.builder()
                .user(userDto)
                .trainerType(data.getTrainerType())
                .employerName(data.getEmployerName())
                .build();
        TrainerDto trainer = trainerService.createTrainer(trainerDto);
        emailService.sendSimpleEmail(userDto.getEmail(), "An account with this email have been created",password);
        return new ResponseEntity<>(trainer, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing trainer",
            description = "Updates fields of a trainer. Any null fields in the request body will keep their existing values.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The attributes the trainer wants to update following the provided schema",
                    content = @Content(schema = @Schema(implementation = TrainerRequestData.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Trainer successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid trainer ID or data provided", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content(mediaType = "text/plain"))
    })
    @PatchMapping("/{id}")
    @UpdateUserInCache
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable Long id, @RequestBody TrainerRequestData data) {
        if (data == null) {
            throw new BadRequestException("The provided trainer is null");
        }
        if (id == null) {
            throw new BadRequestException("The provided trainer id is null");
        }
        TrainerDto oldTrainer = trainerService.getTrainer(id);
        if (oldTrainer == null) {
            throw new UserNotFoundException("Trainer not found");
        }
        UserDto oldUser = oldTrainer.getUser();
        UserDto newUser = UserDto.builder()
                .username(data.getUsername() != null ? data.getUsername() : oldUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : oldUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : oldUser.getDescription())
                .password(oldUser.getPassword())
                .role(Role.TRAINER)
                .userId(oldUser.getUserId())
                .gender(data.getGender() != null ? data.getGender() : oldUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : oldUser.getPhoneNumber())
                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        TrainerDto newTrainer = TrainerDto.builder()
                .trainerId(oldTrainer.getTrainerId())
                .user(oldUser)
                .trainerType(data.getTrainerType() != null ? data.getTrainerType() : oldTrainer.getTrainerType())
                .employerName(data.getEmployerName() != null ? data.getEmployerName() : oldTrainer.getEmployerName())
                .build();
        TrainerDto trainer = trainerService.updateTrainer(newTrainer, newUser);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a trainer by ID",
            description = "Deletes a trainer based on the provided trainer ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Trainer successfully deleted", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided trainer ID is null", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Trainer not found", content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTrainer(@PathVariable Long id) throws UserNotFoundException {
        if (id == null) {
            throw new BadRequestException("Trainer id cannot be null");
        }
        trainerService.deleteTrainer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}