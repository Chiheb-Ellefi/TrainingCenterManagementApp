package com.example.CenterManagement.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class TrainingParticipantsId implements Serializable {
    private String trainingId;
    private Long participantId;

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
