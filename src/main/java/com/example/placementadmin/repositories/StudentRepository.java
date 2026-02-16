package com.example.placementadmin.repositories;

import com.example.placementadmin.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCaseOrHallTicketContainingIgnoreCase(String name, String hallTicket);
}
