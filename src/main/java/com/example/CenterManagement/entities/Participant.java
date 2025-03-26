package com.example.CenterManagement.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;
    @Column(nullable = false,length = 100)
    private String structure ;
    @Column(nullable = false,length = 100)
    private String profile;

}
