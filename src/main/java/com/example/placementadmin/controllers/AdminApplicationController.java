package com.example.placementadmin.controllers;

import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.entities.Application;
import com.example.placementadmin.repositories.AdminRepository;
import com.example.placementadmin.repositories.ApplicationRepository;
import com.example.placementadmin.services.AdminActionLogger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/applications")
@CrossOrigin(origins = "http://localhost:3000") // restrict frontend origin
public class AdminApplicationController {

    private static final Set<String> VALID_STATUSES
            = Set.of("PENDING", "APPROVED", "REJECTED", "UNDER_REVIEW");

    private final ApplicationRepository appRepo;
    private final AdminRepository adminRepo;
    private final AdminActionLogger logger;

    public AdminApplicationController(ApplicationRepository appRepo,
            AdminRepository adminRepo,
            AdminActionLogger logger) {
        this.appRepo = appRepo;
        this.adminRepo = adminRepo;
        this.logger = logger;
    }

    // ================= GET ALL WITH PAGINATION =================
    @GetMapping
    public ResponseEntity<Page<Application>> getAllApplications(Pageable pageable) {
        return ResponseEntity.ok(appRepo.findAll(pageable));
    }

    // ================= UPDATE STATUS =================
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(@PathVariable Long id,
            @RequestParam String status,
            Authentication auth) {

        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        if (status == null || status.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required");
        }

        String normalizedStatus = status.toUpperCase(Locale.ROOT).trim();

        if (!VALID_STATUSES.contains(normalizedStatus)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: " + VALID_STATUSES);
        }

        Application app = appRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));

        Admin admin = adminRepo.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin not found"));

        app.setStatus(normalizedStatus);
        Application saved = appRepo.save(app);

        logger.log(admin.getId(),
                "UPDATE_APPLICATION_STATUS",
                "Application",
                id.toString(),
                "Status changed to: " + normalizedStatus);

        return ResponseEntity.ok(saved);
    }
}
