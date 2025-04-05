package com.example.CenterManagement.dto.user;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployerDto {
    private Long id;
    private String employerName;
}
