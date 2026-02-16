package com.example.placementadmin.dto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
