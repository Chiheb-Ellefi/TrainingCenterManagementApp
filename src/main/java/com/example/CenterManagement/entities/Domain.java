package com.example.CenterManagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "domains")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long domainId;
    @Column(nullable = false,length = 100)
    private String domainName;
}
