package com.example.CenterManagement.mappers.user;

import com.example.CenterManagement.dto.user.ProfileDto;
import com.example.CenterManagement.entities.user.Profile;

public class ProfileMapper {
   public static Profile toEntity(ProfileDto profile) {
        return Profile.builder()
                .profileId(profile.getProfileId())
                .profileType(profile.getProfileType())
                .build();
    }
   public static ProfileDto toDto(Profile profile) {
        return ProfileDto.builder()
                .profileId(profile.getProfileId())
                .profileType(profile.getProfileType())
                .build();
    }
}
