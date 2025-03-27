package com.example.CenterManagement.entities.training;

import com.example.CenterManagement.entities.user.Trainer;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainings")
@Timestamp
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String trainingId;
    @Column(nullable = false,length = 100)
    private String title;
    @Column(nullable = false)
    private Date startDate;
    @Column(nullable = false)
    private Date endDate;
    private Double description;
    private String Domain;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_id", referencedColumnName = "trainerId", nullable = false)
    private Trainer trainer;
}
