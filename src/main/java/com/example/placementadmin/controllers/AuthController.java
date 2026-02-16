package com.example.placementadmin.controllers;

import com.example.placementadmin.dto.LoginRequest;
import com.example.placementadmin.dto.LoginResponse;
import com.example.placementadmin.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        String token = jwtUtils.generateToken(req.getEmail());
        return new LoginResponse(token);
    }
}
