package com.example.CenterManagement.models.dashboardData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class TrainingsDetails {
    private Long nbTrainings;
    private Long totalIncome;
    private List<DomainCount> trainingsPerDomain;
}
