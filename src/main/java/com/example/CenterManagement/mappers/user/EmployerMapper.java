package com.example.CenterManagement.mappers.user;

import com.example.CenterManagement.dto.user.EmployerDto;
import com.example.CenterManagement.entities.user.Employer;

public class EmployerMapper {
    public static EmployerDto toDto(Employer employer) {
        return EmployerDto.builder()
                .id(employer.getId())
                .employerName(employer.getEmployerName())
                .build();
    }
    public static Employer toEntity(EmployerDto dto) {
        return Employer.builder()
                .id(dto.getId())
                .employerName(dto.getEmployerName())
                .build();
    }
}
