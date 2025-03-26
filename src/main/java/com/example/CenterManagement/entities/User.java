package com.example.CenterManagement.entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Timestamp
@Table(name="users",indexes = @Index(columnList = "email",unique = true,name = "email_index"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private Boolean isVerified=false;
    private String  phoneNumber;
    private String secondPhoneNumber;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String profilePicture;
    private String description;
}
