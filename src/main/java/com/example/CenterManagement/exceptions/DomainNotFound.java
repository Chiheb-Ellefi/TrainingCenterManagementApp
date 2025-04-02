package com.example.CenterManagement.exceptions;

public class DomainNotFound extends RuntimeException {
    public DomainNotFound(String message) {
        super(message);
    }
}
