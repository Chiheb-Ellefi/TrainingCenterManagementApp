package com.example.CenterManagement.models;

import lombok.*;

@Getter
@Builder
@Setter
public class ErrorDetails {
    private  String message;
    private  String details;
}
