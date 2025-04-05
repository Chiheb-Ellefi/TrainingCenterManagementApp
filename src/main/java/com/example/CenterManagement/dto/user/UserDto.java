package com.example.CenterManagement.dto.user;

import com.example.CenterManagement.entities.user.Gender;
import com.example.CenterManagement.entities.user.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private Role role;
    private Boolean isVerified;
    private String phoneNumber;
    private String secondPhoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private Gender gender;
    private String profilePicture;
    private String description;
}