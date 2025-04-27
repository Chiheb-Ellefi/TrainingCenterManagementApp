package com.example.CenterManagement.mappers.training;

import com.example.CenterManagement.dto.training.DomainDto;
import com.example.CenterManagement.entities.training.Domain;

public class DomainMapper {
    public static Domain toEntity(DomainDto domainDto) {
        return Domain.builder()
                .domainId(domainDto.getDomainId())
                .domainName(domainDto.getDomainName())
                .build();

    }
    public static DomainDto toDto(Domain domain) {
        return DomainDto.builder()
                .domainId(domain.getDomainId())
                .domainName(domain.getDomainName())
                .build();
    }
}
