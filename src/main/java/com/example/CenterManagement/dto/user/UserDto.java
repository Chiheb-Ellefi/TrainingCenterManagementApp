package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Role role;
    private Boolean isVerified;
    private String  phoneNumber;
    private String secondPhoneNumber;
    private Date dateOfBirth;
    private Gender gender;
    private String profilePicture;
    private String description;
}
