package com.example.placementadmin.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "certificates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Drive drive;

    private String certTokenHash;

    @ManyToOne
    @JoinColumn(name = "issued_by_admin_id")
    private Admin issuedBy;

    private Instant issuedAt = Instant.now();
}
