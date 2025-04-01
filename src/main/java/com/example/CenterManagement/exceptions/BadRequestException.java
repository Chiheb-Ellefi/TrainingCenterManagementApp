package com.example.CenterManagement.exceptions;

public class BadRequestException extends RuntimeException {
   public BadRequestException(String details) {
       super(details);
   }
}
