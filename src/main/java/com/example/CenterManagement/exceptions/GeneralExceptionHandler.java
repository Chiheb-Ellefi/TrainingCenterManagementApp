package com.example.CenterManagement.exceptions;


import com.example.CenterManagement.exceptions.trainings.DomainNotFoundException;
import com.example.CenterManagement.exceptions.trainings.EnrollmentNotFoundException;
import com.example.CenterManagement.exceptions.trainings.ParticipantAlreadyEnrolledException;
import com.example.CenterManagement.exceptions.trainings.TrainingNotFoundException;
import com.example.CenterManagement.exceptions.users.EmployerNotFoundException;
import com.example.CenterManagement.exceptions.users.ProfileNotFoundException;
import com.example.CenterManagement.exceptions.users.StructureNotFoundException;
import com.example.CenterManagement.exceptions.users.UserNotFoundException;
import com.example.CenterManagement.models.ErrorDetails;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> userNotFoundHandler(UserNotFoundException e) {
        ErrorDetails errorDetails =ErrorDetails.builder()
                .details(e.getMessage())
                .message("User not found")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TrainingNotFoundException.class)
    public ResponseEntity<ErrorDetails> trainingNotFoundHandler(TrainingNotFoundException e) {
        ErrorDetails errorDetails =ErrorDetails.builder()
                .details(e.getMessage())
                .message("Training not found")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ParticipantAlreadyEnrolledException.class)
    public ResponseEntity<ErrorDetails> participantAlreadyEnrolledHandler(ParticipantAlreadyEnrolledException e) {
        ErrorDetails errorDetails =ErrorDetails.builder()
                .details(e.getMessage())
                .message("Participant already enrolled to this training")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(EnrollmentNotFoundException.class)
   public ResponseEntity<ErrorDetails> enrollmentNotFoundHandler(EnrollmentNotFoundException e) {
        ErrorDetails errorDetails =ErrorDetails.builder()
                .details(e.getMessage())
                .message("Enrollment not found for this participant")
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> badRequestHandler(BadRequestException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Invalid request body")
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<ErrorDetails> domainNotFoundHandler(DomainNotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Provided domain name does not exist")
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmployerNotFoundException.class)
    public ResponseEntity<ErrorDetails> employerNotFoundHandler(EmployerNotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Provided employer name does not exist")
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorDetails> profileNotFoundHandler(ProfileNotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Provided profile does not exist")
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StructureNotFoundException.class)
    public ResponseEntity<ErrorDetails> structureNotFoundHandler(StructureNotFoundException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Provided structure name does not exist")
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> runtimeExceptionHandler(RuntimeException ex) {

        String message = "An unexpected error occurred";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        Throwable cause = ex.getCause();
        if(cause ==null) {
            cause = ex;
        }
        if (ex instanceof DataIntegrityViolationException) {
            message = "Database integrity violation - likely due to a constraint failure.";
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof PropertyValueException) {
            message = "A required field is missing or null.";
            status = HttpStatus.BAD_REQUEST;
        } else if (cause instanceof ConstraintViolationException) {
            message = "A database constraint was violated. Check unique or foreign key constraints.";
            status = HttpStatus.BAD_REQUEST;
        } else if (cause instanceof SQLIntegrityConstraintViolationException) {
            message = "SQL integrity constraint violated - likely a duplicate or foreign key issue.";
            status = HttpStatus.BAD_REQUEST;
        } else if (cause instanceof InvalidFormatException) {
            message = "An invalid format occurred";
            status = HttpStatus.BAD_REQUEST;

        }

        ErrorDetails errorDetails = ErrorDetails.builder()
                .message(message)
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, status);
    }


}
