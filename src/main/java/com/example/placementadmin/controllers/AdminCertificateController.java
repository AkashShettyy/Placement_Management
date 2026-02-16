package com.example.placementadmin.controllers;

import com.example.placementadmin.entities.*;
import com.example.placementadmin.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin/certificates")
public class AdminCertificateController {

    @Autowired
    private CertificateRepository certRepo;

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private DriveRepository driveRepo;

    @Autowired
    private AdminRepository adminRepo;

    @PostMapping("/issue")
    public Certificate issueCertificate(@RequestParam Long studentId,
                                        @RequestParam Long driveId,
                                        Authentication auth) {
        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow();
        Student student = studentRepo.findById(studentId).orElseThrow();
        Drive drive = driveRepo.findById(driveId).orElseThrow();

        Certificate cert = new Certificate();
        cert.setStudent(student);
        cert.setDrive(drive);
        cert.setIssuedBy(admin);
        cert.setCertTokenHash(UUID.randomUUID().toString());

        return certRepo.save(cert);
    }

    @GetMapping("/verify/{token}")
    public String verifyCertificate(@PathVariable String token) {
        return certRepo.findAll().stream()
                .filter(c -> token.equals(c.getCertTokenHash()))
                .findFirst()
                .map(c -> "Valid certificate for Student: " + c.getStudent().getName())
                .orElse("Invalid certificate token");
    }
}
