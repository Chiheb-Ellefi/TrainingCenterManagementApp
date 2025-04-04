package com.example.CenterManagement.controllers.training;

import com.example.CenterManagement.dto.training.TrainingDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.exceptions.trainings.TrainingNotFoundException;
import com.example.CenterManagement.models.TrainingRequestData;
import com.example.CenterManagement.services.training.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings(@RequestParam(required = false,defaultValue = "0") Integer page) {
        List<TrainingDto> trainings = trainingService.getAllTrainings(page);
        return ResponseEntity.ok(trainings);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getTrainingById(@PathVariable("id") String id) {
        if(id == null) {
            throw new BadRequestException("Training id cannot be null");
        }
        TrainingDto training =trainingService.getTrainingById(id);
        return ResponseEntity.ok(training);
    }
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingRequestData data) {
        if(data == null) {
            throw new BadRequestException("Request data  cannot be null");
        }

        TrainingDto trainingDto=TrainingDto.builder()
                .title(data.getTitle())
                .startDate(data.getStartDate())
                .endDate(data.getEndDate())
                .description(data.getDescription())
                .domainName(data.getDomainName())
                .build();

        TrainingDto savedTraining = trainingService.createTraining(trainingDto, data.getTrainerId());
        return new ResponseEntity<>(savedTraining, HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable("id") String id, @RequestBody TrainingRequestData data) {
        if(id == null) {
            throw new BadRequestException("Training id cannot be null");
        }
        if(data == null) {
            throw new BadRequestException("Request data  cannot be null");
        }
        TrainingDto oldTraining = trainingService.getTrainingById(id);
        if(oldTraining == null) {
            throw new TrainingNotFoundException("Training is not found");
        }
        TrainingDto newTraining=TrainingDto.builder()
                .trainingId(oldTraining.getTrainingId())
                .title(data.getTitle()!=null?data.getTitle():oldTraining.getTitle())
                .startDate(data.getStartDate()!=null?data.getStartDate():oldTraining.getStartDate())
                .endDate(data.getEndDate()!=null?data.getEndDate():oldTraining.getEndDate())
                .description(data.getDescription()!=null?data.getDescription():oldTraining.getDescription())
                .domainName(data.getDomainName()!=null?data.getDomainName():oldTraining.getDomainName())
                .build();
        Long trainerId=data.getTrainerId()!=null? data.getTrainerId() : oldTraining.getTrainer().getTrainerId();
        TrainingDto updatedTraining=trainingService.createTraining(newTraining,trainerId);
        return new ResponseEntity<>(updatedTraining, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTraining(@PathVariable("id") String id) {
        if(id == null) {
            throw new BadRequestException("Training id cannot be null");
        }
        trainingService.deleteTrainingById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
