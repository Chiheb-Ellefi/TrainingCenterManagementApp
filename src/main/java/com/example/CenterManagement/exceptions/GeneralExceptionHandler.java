package com.example.CenterManagement.exceptions;

import com.example.CenterManagement.dto.user.UserDto;
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
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> badRequestHandler(BadRequestException ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Bad request")
                .details(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DomainNotFound.class)
    public ResponseEntity<ErrorDetails> domainNotFoundHandler(DomainNotFound ex) {
        ErrorDetails errorDetails = ErrorDetails.builder()
                .message("Domain name does not exist")
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
