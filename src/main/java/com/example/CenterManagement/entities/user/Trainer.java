package com.example.CenterManagement.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TrainerType trainerType;
}
