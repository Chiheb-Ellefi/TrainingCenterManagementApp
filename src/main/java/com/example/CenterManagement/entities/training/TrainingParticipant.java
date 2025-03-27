package com.example.CenterManagement.entities.training;

import com.example.CenterManagement.entities.user.Participant;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training_participants")
public class TrainingParticipant {

    @EmbeddedId
    private TrainingParticipantsId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("trainingId")
    @JoinColumn(name = "training_id", referencedColumnName = "trainingId")
    private Training training;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("participantId")
    @JoinColumn(name = "participant_id", referencedColumnName = "participantId")
    private Participant participant;

}
