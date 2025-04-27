package com.example.CenterManagement.models.dashboardData;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainCount {
    private String domainName;
    private Long count;
}
