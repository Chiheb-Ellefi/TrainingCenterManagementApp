package com.example.CenterManagement.models;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.Role;
import com.example.CenterManagement.entities.user.TrainerType;
import lombok.Getter;


import java.util.Date;

@Getter
public class UserRequestData {
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
    private TrainerType trainerType;
    private String employerName;
    private String structure ;
    private String profile;
}
