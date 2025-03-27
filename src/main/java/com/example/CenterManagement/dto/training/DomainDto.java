package com.example.CenterManagement.dto.training;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DomainDto {
    private Long domainId;
    private String domainName;
}
