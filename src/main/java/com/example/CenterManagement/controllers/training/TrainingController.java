package com.example.CenterManagement.controllers.training;

import com.example.CenterManagement.annotations.training.CheckInCache;
import com.example.CenterManagement.annotations.training.UpdateTrainingInCache;
import com.example.CenterManagement.dto.training.DomainDto;
import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.exceptions.trainings.TrainingNotFoundException;
import com.example.CenterManagement.models.requestData.TrainingRequestData;
import com.example.CenterManagement.services.training.DomainService;
import com.example.CenterManagement.services.training.TrainingService;
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
@RequestMapping("/api/v1/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    private final DomainService domainService;
    @Autowired
    public TrainingController(TrainingService trainingService, DomainService domainService) {
        this.trainingService = trainingService;
        this.domainService = domainService;
    }

    @Operation(
            summary = "Get paginated list of all trainings",
            description = "Retrieves a list of trainings based on the provided page number. Default is page 0.",
            parameters = {
                    @Parameter(name = "page", description = "Page number for pagination")
            }
    )
    @ApiResponse(responseCode = "200", description = "List of trainings successfully retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = TrainingDto.class))))
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings(@RequestParam(required = false, defaultValue = "0") Integer page) {
        List<TrainingDto> trainings = trainingService.getAllTrainings(page);
        return ResponseEntity.ok(trainings);
    }

    @Operation(
            summary = "Get a training by ID",
            description = "Fetches a training using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Training found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDto.class))),
            @ApiResponse(responseCode = "400", description = "Provided training ID is null",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Training not found",
                    content = @Content(mediaType = "text/plain"))
    })
    @CheckInCache
    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable("id") String id) {
        if (id == null) {
            throw new BadRequestException("Training id cannot be null");
        }
        TrainingDto training = trainingService.getTrainingById(id);
        return ResponseEntity.ok(training);
    }

    @Operation(
            summary = "Create a new training",
            description = "Creates a new training with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The necessary attributes to create a new training",
                    content = @Content(schema = @Schema(implementation = TrainingRequestData.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Training created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data was provided, check the request body",
                    content = @Content(mediaType = "text/plain"))
    })
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingRequestData data) {
        if (data == null) {
            throw new BadRequestException("Request data cannot be null");
        }
        DomainDto domain=domainService.getDomain(data.getDomainId());
        TrainingDto trainingDto = TrainingDto.builder()
                .title(data.getTitle())
                .startDate(data.getStartDate())
                .endDate(data.getEndDate())
                .description(data.getDescription())
                .type(data.getType())
                .price(data.getPrice())
                .endTime(data.getEndTime())
                .startTime(data.getStartTime())
                .price(data.getPrice())
                .domain(domain)
                .build();

        TrainingDto savedTraining = trainingService.createTraining(trainingDto, data.getTrainerId());
        return new ResponseEntity<>(savedTraining, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing training",
            description = "Updates fields of a training. Any null fields in the request body will keep their existing values.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "The attributes the user wants to update following the provided schema",
                    content = @Content(schema = @Schema(implementation = TrainingRequestData.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Training successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid training ID or request data provided",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "404", description = "Training not found",
                    content = @Content(mediaType = "text/plain"))
    })
    @PatchMapping("/{id}")
    @UpdateTrainingInCache
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable("id") String id, @RequestBody TrainingRequestData data) {
        if (id == null) {
            throw new BadRequestException("Training id cannot be null");
        }
        if (data == null) {
            throw new BadRequestException("Request data cannot be null");
        }

        TrainingDto oldTraining = trainingService.getTrainingById(id);
        if (oldTraining == null) {
            throw new TrainingNotFoundException("Training is not found");
        }

        TrainingDto newTraining = TrainingDto.builder()
                .trainingId(oldTraining.getTrainingId())
                .title(data.getTitle() != null ? data.getTitle() : oldTraining.getTitle())
                .startDate(data.getStartDate() != null ? data.getStartDate() : oldTraining.getStartDate())
                .endDate(data.getEndDate() != null ? data.getEndDate() : oldTraining.getEndDate())
                .description(data.getDescription() != null ? data.getDescription() : oldTraining.getDescription())
                .domain(data.getDomainId() != null ? domainService.getDomain(data.getDomainId()): oldTraining.getDomain())
                .startTime(data.getStartTime() != null ? data.getStartTime() : oldTraining.getStartTime())
                .endTime(data.getEndTime() != null ? data.getEndTime() : oldTraining.getEndTime())
                .price(data.getPrice() != null ? data.getPrice() : oldTraining.getPrice())
                .type(data.getType() != null ? data.getType() : oldTraining.getType())
                .build();

        Long trainerId = data.getTrainerId() != null ? data.getTrainerId() : oldTraining.getTrainer().getTrainerId();
        TrainingDto updatedTraining = trainingService.createTraining(newTraining, trainerId);
        return new ResponseEntity<>(updatedTraining, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a training by ID",
            description = "Deletes a training based on the provided training ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Training successfully deleted",
                    content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "400", description = "Provided training ID is null",
                    content = @Content(mediaType = "text/plain"))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable("id") String id) {
        if (id == null) {
            throw new BadRequestException("Training id cannot be null");
        }
        trainingService.deleteTrainingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}