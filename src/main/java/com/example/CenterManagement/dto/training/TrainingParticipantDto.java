package com.example.CenterManagement.dto.training;

import com.example.CenterManagement.dto.user.ParticipantDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TrainingParticipantDto {
    private TrainingDto training;
    private ParticipantDto participant;
}
