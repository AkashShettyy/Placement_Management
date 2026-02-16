package com.example.placementadmin.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long adminId;
    private String action;
    private String entity;
    private String entityId;

    @Lob
    private String details;

    private Instant timestamp = Instant.now();
}
