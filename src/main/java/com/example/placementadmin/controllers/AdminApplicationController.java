package com.example.placementadmin.controllers;

import com.example.placementadmin.dto.StatusUpdateRequest;
import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.entities.Application;
import com.example.placementadmin.repositories.AdminRepository;
import com.example.placementadmin.repositories.ApplicationRepository;
import com.example.placementadmin.services.AdminActionLogger;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/api/admin/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminApplicationController {

              8 compatible
    private static final Set<String> VALID_STATUSES =
            new HashSet<>(Arrays.asList("PENDING", "APPROVED", "REJECTED", "UNDER_REVIEW"));

    private final ApplicationRepository appRepo;
    private final AdminRepository adminRepo;
    private final AdminActionLogger logger;
            
            epo,
                                      AdminRepository adminRepo,
                                      AdminActionLogger logger) {
        this.appRepo = appRepo;
        this.adminRepo = adminRepo;
        this.logger = logger;
    }

    // ================= GET ALL =================
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(appRepo.findAll());
    }

    // ================= UPDATE STATUS =================
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request,
            Authentication auth) {

        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }

        if (request.getStatus() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is required");
        }

        String normalizedStatus = request.getStatus().toUpperCase(Locale.ROOT);

        if (!VALID_STATUSES.contains(normalizedStatus)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed: " + VALID_STATUSES
            );
        }
                          
        Application app = appRepo.findById(id)
                .orElseThrow(() ->
                        new Res
                         on 
        Admin admin = adminRepo.findByEmail(auth.getName())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin not found"));

        app.setStatus(normalizedStatus);

        logger.log(admin.getId(),
                "UPDATE_APPLICATION_STATUS",
                "Application",
                id.toString(),
                "Status changed to: " + normalizedStatus);

        return ResponseEntity.ok(app);
    }
}
