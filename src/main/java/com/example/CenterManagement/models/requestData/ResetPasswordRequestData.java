package com.example.CenterManagement.models.requestData;

import lombok.Data;

@Data
public class ResetPasswordRequestData {
    private String email;
    private String password;
    private String code;
}
