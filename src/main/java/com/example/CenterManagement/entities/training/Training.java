package com.example.CenterManagement.entities.training;

import com.example.CenterManagement.entities.user.Trainer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.*;

import java.util.Date;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
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
    private String description;
    private String domainName;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_id", referencedColumnName = "trainerId", nullable = false)
    private Trainer trainer;
}
