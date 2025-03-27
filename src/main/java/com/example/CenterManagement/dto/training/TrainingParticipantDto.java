package com.example.CenterManagement.dto.training;

import com.example.CenterManagement.dto.user.ParticipantDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TrainingParticipantDto {
    private TrainingDto training;
    private ParticipantDto participant;
}
