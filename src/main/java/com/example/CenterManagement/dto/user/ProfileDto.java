package com.example.CenterManagement.dto.user;

import lombok.*;

@Builder
@Getter
public class ProfileDto {
    private Long profileId;
    private String profileType;
}