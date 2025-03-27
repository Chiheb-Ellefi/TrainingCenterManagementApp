package com.example.CenterManagement.mappers.user;

import com.example.CenterManagement.dto.user.UserDto;
import com.example.CenterManagement.entities.user.User;

public class UserMapper {
    public static User  toEntity(UserDto dto){
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .userId(dto.getUserId())
                .dateOfBirth(dto.getDateOfBirth())
                .description(dto.getDescription())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .isVerified(dto.getIsVerified())
                .phoneNumber(dto.getPhoneNumber())
                .profilePicture(dto.getProfilePicture())
                .role(dto.getRole())
                .secondPhoneNumber(dto.getSecondPhoneNumber())
                .build();
    }
    public static UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .userId(user.getUserId())
                .dateOfBirth(user.getDateOfBirth())
                .description(user.getDescription())
                .email(user.getEmail())
                .gender(user.getGender())
                .isVerified(user.getIsVerified())
                .phoneNumber(user.getPhoneNumber())
                .profilePicture(user.getProfilePicture())
                .role(user.getRole())
                .secondPhoneNumber(user.getSecondPhoneNumber())
                .build();
    }
}
