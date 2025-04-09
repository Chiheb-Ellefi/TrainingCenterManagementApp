package com.example.CenterManagement.controllers.users;

import com.example.CenterManagement.annotations.users.CheckInCache;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.Role;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.ParticipantRequestData;
import com.example.CenterManagement.services.users.EmailService;
import com.example.CenterManagement.services.users.ParticipantService;
import com.example.CenterManagement.utils.RandomPasswordGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController {
    private final ParticipantService participantService;
    private final EmailService emailService;
    @Autowired
    public ParticipantController(ParticipantService participantService,EmailService emailService) {
        this.participantService = participantService;
        this.emailService = emailService;
    }

    @Operation(
            summary = "Get paginated list of all participants",
            description = "Retrieves a list of participants based on the provided page number. Default is page 0.",
            parameters = {
                    @Parameter(name = "page", description = "Page number for pagination")
            }
    )
    @ApiResponse(responseCode = "200", description = "List of participants successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ParticipantDto.class))))
    @GetMapping
    public ResponseEntity<List<ParticipantDto>> getAllParticipants(@RequestParam(required = false, defaultValue = "0") Integer page) {
        List<ParticipantDto> participants = participantService.getAllParticipants(page);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    @Operation(
            summary = "Get a participant by ID",
            description = "Fetches a participant using their unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Participant found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class))),
            @ApiResponse(responseCode = "400", description = "Provided participant ID is null", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Participant not found", content = @Content(mediaType = "text/plain"))
    })
    @CheckInCache(type = "participant")
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDto> getParticipant(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Provided participant id is null");
        }
        ParticipantDto participant = participantService.getParticipant(id);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    @Operation(
            summary = "Create a new participant",
            description = "Creates a new participant with the provided data. A random password is generated for the participant.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new participant",
                    content = @Content(schema = @Schema(implementation = ParticipantRequestData.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Participant created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body", content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<ParticipantDto> createParticipant(@RequestBody ParticipantRequestData data) {
        if (data == null || data.getEmail() == null || data.getUsername() == null) {
            throw new BadRequestException("Provided participant data is null or empty");
        }
        String password = RandomPasswordGenerator.generateRandomPassword();
        UserDto user = UserDto.builder()
                .email(data.getEmail())
                .username(data.getUsername())
                .description(data.getDescription())
                .dateOfBirth(data.getDateOfBirth())
                .phoneNumber(data.getPhoneNumber())
                .role(Role.PARTICIPANT)
                .password(password)
                .gender(data.getGender())
                .profilePicture(data.getProfilePicture())
                .build();
        ParticipantDto participantDto = ParticipantDto.builder()
                .user(user)
                .structure(data.getStructure())
                .profile(data.getProfile())
                .build();
        ParticipantDto participant = participantService.createParticipant(participantDto);
        emailService.sendSimpleEmail(user.getEmail(), "An account with this email have been created",password);
        return new ResponseEntity<>(participant, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing participant",
            description = "Updates fields of a participant. Any null fields in the request body will keep their existing values.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The attributes the participant wants to update following the provided schema",
                    content = @Content(schema = @Schema(implementation = ParticipantRequestData.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Participant successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ParticipantDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid participant ID or data provided", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Participant not found", content = @Content(mediaType = "text/plain"))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ParticipantDto> updateParticipant(@PathVariable Long id, @RequestBody ParticipantRequestData data) {
        if (id == null) {
            throw new BadRequestException("Provided participantId is null ");
        }
        if (data == null) {
            throw new BadRequestException("Provided participant data is null ");
        }
        ParticipantDto participant = participantService.getParticipant(id);
        if (participant == null) {
            throw new UserNotFoundException("User not found");
        }
        UserDto oldUser = participant.getUser();
        UserDto newUser = UserDto.builder()
                .username(data.getUsername() != null ? data.getUsername() : oldUser.getUsername())
                .email(data.getEmail() != null ? data.getEmail() : oldUser.getEmail())
                .dateOfBirth(data.getDateOfBirth() != null ? data.getDateOfBirth() : oldUser.getDateOfBirth())
                .description(data.getDescription() != null ? data.getDescription() : oldUser.getDescription())
                .password(oldUser.getPassword())
                .role(Role.PARTICIPANT)
                .userId(participant.getUser().getUserId())
                .gender(data.getGender() != null ? data.getGender() : oldUser.getGender())
                .phoneNumber(data.getPhoneNumber() != null ? data.getPhoneNumber() : oldUser.getPhoneNumber())

                .profilePicture(data.getProfilePicture() != null ? data.getProfilePicture() : oldUser.getProfilePicture())
                .build();
        ParticipantDto newParticipant = ParticipantDto.builder()
                .participantId(participant.getParticipantId())
                .user(newUser)
                .structure(data.getStructure() != null ? data.getStructure() : participant.getStructure())
                .profile(data.getProfile() != null ? data.getProfile() : participant.getProfile())
                .build();
        ParticipantDto participantDto = participantService.updateParticipant(newParticipant);
        return new ResponseEntity<>(participantDto, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Delete a participant by ID",
            description = "Deletes a participant based on the provided participant ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Participant successfully deleted", content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided participant ID is null", content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestException("Provided participant id is null ");
        }
        participantService.deleteParticipant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}