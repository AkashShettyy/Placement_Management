package com.example.placementadmin.controllers;

import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.entities.Drive;
import com.example.placementadmin.repositories.AdminRepository;
import com.example.placementadmin.repositories.DriveRepository;
import com.example.placementadmin.services.AdminActionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/drives")
public class AdminDriveController {

    @Autowired
    private DriveRepository driveRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AdminActionLogger logger;

    @PostMapping
    public Drive createDrive(@RequestBody Drive drive, Authentication auth) {
        Drive saved = driveRepo.save(drive);
        Long adminId = adminRepo.findByEmail(auth.getName()).map(Admin::getId).orElse(null);
        logger.log(adminId, "CREATE_DRIVE", "Drive", saved.getId().toString(), saved.getTitle());
        return saved;
    }

    @GetMapping
    public List<Drive> getAllDrives() {
        return driveRepo.findAll();
    }
}
