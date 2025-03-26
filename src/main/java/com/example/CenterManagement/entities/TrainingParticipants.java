package com.example.CenterManagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "training_participants")
public class TrainingParticipants {

    @EmbeddedId
    private TrainingParticipantsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trainingId")
    @JoinColumn(name = "training_id", referencedColumnName = "trainingId")
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("participantId")
    @JoinColumn(name = "participant_id", referencedColumnName = "userId")
    private User participant;

}
