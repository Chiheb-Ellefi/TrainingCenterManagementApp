package com.example.CenterManagement.entities.training;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class TrainingParticipantsId implements Serializable {

    private String trainingId;
    private Long participantId;



    public TrainingParticipantsId() {}

    public TrainingParticipantsId(String trainingId, Long participantId) {
        this.trainingId = trainingId;
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingParticipantsId that = (TrainingParticipantsId) o;
        return Objects.equals(trainingId, that.trainingId) &&
                Objects.equals(participantId, that.participantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, participantId);
    }
}
