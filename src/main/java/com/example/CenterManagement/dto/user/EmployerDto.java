package com.example.CenterManagement.dto.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class EmployerDto {
    private Long id;
    private String employerName;
}
