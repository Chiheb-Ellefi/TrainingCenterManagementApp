package com.example.CenterManagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    private Long profileId;
    @Column(nullable = false,length = 100)
    private String profileType;
}
