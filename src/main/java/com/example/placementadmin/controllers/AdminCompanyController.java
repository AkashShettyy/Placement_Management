package com.example.placementadmin.controllers;

import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.entities.Company;
import com.example.placementadmin.repositories.AdminRepository;
import com.example.placementadmin.repositories.CompanyRepository;
import com.example.placementadmin.services.AdminActionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/companies")
public class AdminCompanyController {

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AdminActionLogger logger;

    @GetMapping
    public List<Company> listCompanies() {
        return companyRepo.findAll();
    }

    @PutMapping("/{id}/approve")
    public Company approve(@PathVariable Long id, Authentication auth) {
        Company c = companyRepo.findById(id).orElseThrow();
        c.setApproved(true);
        Company saved = companyRepo.save(c);
        Long adminId = adminRepo.findByEmail(auth.getName()).map(Admin::getId).orElse(null);
        logger.log(adminId, "APPROVE_COMPANY", "Company", id.toString(), c.getName());
        return saved;
    }
}
