package com.example.CenterManagement.models.dashboardData;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TrainingsDetails {
    private Long nbTrainings;
    private Long totalIncome;
}
