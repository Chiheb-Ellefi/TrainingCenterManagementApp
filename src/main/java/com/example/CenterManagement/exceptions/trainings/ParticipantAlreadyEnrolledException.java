package com.example.CenterManagement.exceptions.trainings;

public class ParticipantAlreadyEnrolledException extends RuntimeException {
    public ParticipantAlreadyEnrolledException(String message) {
        super(message);
    }
}
