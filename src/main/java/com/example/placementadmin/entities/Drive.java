package com.example.placementadmin.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "drives")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;
    private LocalDate date;
    private String venue;

    @Lob
    private String eligibilityJson;

    private String status = "SCHEDULED";
}
