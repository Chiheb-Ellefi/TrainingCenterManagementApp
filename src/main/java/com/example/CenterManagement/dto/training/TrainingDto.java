package com.example.CenterManagement.dto.training;

import com.example.CenterManagement.dto.user.TrainerDto;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class TrainingDto {
    private String trainingId;
    private String title;
    private Date startDate;
    private Date endDate;
    private Double description;
    private String Domain;
    private TrainerDto trainer;
}
