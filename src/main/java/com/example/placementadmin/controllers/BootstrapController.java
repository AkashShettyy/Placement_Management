package com.example.placementadmin.controllers;

import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bootstrap")
public class BootstrapController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/create-admin")
    public String createAdmin(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String password) {
        if (adminRepo.findByEmail(email).isPresent()) {
            return "Admin already exists!";
        }
        Admin admin = new Admin();
        admin.setName(name);
        admin.setEmail(email);
        admin.setPasswordHash(encoder.encode(password));
        admin.setRole("ROLE_ADMIN");
        adminRepo.save(admin);
        return "Admin created successfully!";
    }
}
