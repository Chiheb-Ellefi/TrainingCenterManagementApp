package com.example.CenterManagement.dto.user;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long profileId;
    private String profileType;
}