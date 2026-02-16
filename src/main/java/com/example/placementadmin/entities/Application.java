package com.example.placementadmin.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drive_id")
    private Drive drive;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String status = "APPLIED";
    private Instant appliedAt = Instant.now();

    // Placement details (optional)
    private String jobTitle;
    private BigDecimal salary;
    private LocalDate placementDate;
}
