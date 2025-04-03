package com.example.CenterManagement.controllers.training;

import com.example.CenterManagement.dto.training.TrainingParticipantDto;
import com.example.CenterManagement.dto.user.ParticipantDto;
import com.example.CenterManagement.exceptions.BadRequestException;
import com.example.CenterManagement.services.training.TrainingEnrollmentService;
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
    @PostMapping("/{id}/enroll/{user_id}")
    public ResponseEntity<TrainingParticipantDto> enrollTraining(@PathVariable String id,@PathVariable Long user_id) throws BadRequestException {
        if(id==null||user_id==null){
             throw new BadRequestException("The provided training id or user_id is null");
        }

       TrainingParticipantDto trainingParticipantDto= trainingEnrollmentService.createTrainingEnrollment(id,user_id);
        return new ResponseEntity<>(trainingParticipantDto, HttpStatus.CREATED);
    }
    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantDto>> getTrainingParticipants(@PathVariable String id){
        if(id==null){
            throw new BadRequestException("The provided training id is null");
        }
        List<ParticipantDto> response=trainingEnrollmentService.getEnrollmentParticipants(id);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @DeleteMapping("/{id}/unenroll/{pid}")
    public ResponseEntity<String> unenrollTraining(@PathVariable String id,@PathVariable Long pid){
        if(id==null ||pid==null){
            throw new BadRequestException("The provided training id or pid is null");
        }
        trainingEnrollmentService.cancelTrainingEnrollment(pid,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);


    }
}
