package com.example.CenterManagement.entities.user;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;


import java.util.Date;

@Getter
@Setter

@Entity
@Timestamp
@Table(name="users",indexes = @Index(columnList = "email",unique = true,name = "email_index"))
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isVerified;
    private String  phoneNumber;
    private String secondPhoneNumber;
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String profilePicture;
    private String description;


}
