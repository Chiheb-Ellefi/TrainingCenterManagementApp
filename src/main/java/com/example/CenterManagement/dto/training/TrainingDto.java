package com.example.CenterManagement.dto.training;

import com.example.CenterManagement.dto.user.TrainerDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class TrainingDto {
    private String trainingId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;
    private String domainName;
    private TrainerDto trainer;
}
