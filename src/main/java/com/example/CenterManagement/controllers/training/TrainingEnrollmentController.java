package com.example.CenterManagement.controllers.training;

import com.example.CenterManagement.dto.training.TrainingParticipantDto;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.services.training.TrainingEnrollmentService;
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
@RequestMapping("/api/v1/trainings")
public class TrainingEnrollmentController {
    private final TrainingEnrollmentService trainingEnrollmentService;

    @Autowired
    public TrainingEnrollmentController(TrainingEnrollmentService trainingEnrollmentService) {
        this.trainingEnrollmentService = trainingEnrollmentService;
    }

    @Operation(
            summary = "Enroll a participant in a training",
            description = "Enrolls a participant (user) into a specific training using the training ID and user ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Participant successfully enrolled in the training",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingParticipantDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid training ID or user ID provided",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping("/{id}/enroll/{user_id}")
    public ResponseEntity<TrainingParticipantDto> enrollTraining(@PathVariable String id, @PathVariable Long user_id) throws BadRequestException {
        if (id == null || user_id == null) {
            throw new BadRequestException("The provided training id or user_id is null");
        }

        TrainingParticipantDto trainingParticipantDto = trainingEnrollmentService.createTrainingEnrollment(id, user_id);
        return new ResponseEntity<>(trainingParticipantDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get all participants of a training",
            description = "Retrieves a list of participants enrolled in a specific training using the training ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of participants successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ParticipantDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid training ID provided",
                    content = @Content(mediaType = "text/plain"))
    })
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDto>> getTrainingParticipants(@PathVariable String id) {
        if (id == null) {
            throw new BadRequestException("The provided training id is null");
        }
        List<ParticipantDto> response = trainingEnrollmentService.getEnrollmentParticipants(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Unenroll a participant from a training",
            description = "Removes a participant (user) from a specific training using the training ID and participant ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Participant successfully unenrolled from the training",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Invalid training ID or participant ID provided",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}/unenroll/{pid}")
    public ResponseEntity<String> unenrollTraining(@PathVariable String id, @PathVariable Long pid) {
        if (id == null || pid == null) {
            throw new BadRequestException("The provided training id or pid is null");
        }
        trainingEnrollmentService.cancelTrainingEnrollment(pid, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}