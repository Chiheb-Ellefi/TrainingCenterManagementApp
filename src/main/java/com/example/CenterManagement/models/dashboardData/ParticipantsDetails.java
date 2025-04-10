package com.example.CenterManagement.models.dashboardData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
@Getter
@Setter
@Builder
public class ParticipantsDetails {
    private Long nbParticipants;
    private HashMap<String, Long> topParticipants;

}
