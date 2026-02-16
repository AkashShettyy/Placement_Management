package com.example.placementadmin.controllers;

import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.entities.Student;
import com.example.placementadmin.repositories.AdminRepository;
import com.example.placementadmin.repositories.StudentRepository;
import com.example.placementadmin.services.AdminActionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/students")
public class AdminStudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AdminActionLogger logger;

    @PostMapping
    public Student addStudent(@RequestBody Student student, Authentication auth) {
        Student saved = studentRepo.save(student);
        logAction(auth, "ADD_STUDENT", saved.getId().toString(), saved.getName());
        return saved;
    }

    @GetMapping
    public List<Student> listAll() {
        return studentRepo.findAll();
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student s, Authentication auth) {
        Student existing = studentRepo.findById(id).orElseThrow();
        existing.setName(s.getName());
        existing.setEmail(s.getEmail());
        existing.setCourse(s.getCourse());
        existing.setPassYear(s.getPassYear());
        existing.setQualification(s.getQualification());
        Student saved = studentRepo.save(existing);
        logAction(auth, "UPDATE_STUDENT", id.toString(), saved.getName());
        return saved;
    }

    private void logAction(Authentication auth, String action, String entityId, String details) {
        Long adminId = adminRepo.findByEmail(auth.getName()).map(Admin::getId).orElse(null);
        logger.log(adminId, action, "Student", entityId, details);
    }
}
