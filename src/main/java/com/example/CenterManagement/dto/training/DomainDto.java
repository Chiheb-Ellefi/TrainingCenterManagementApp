package com.example.CenterManagement.dto.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DomainDto {
    private Long domainId;
    private String domainName;
}
