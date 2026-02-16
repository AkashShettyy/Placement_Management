package com.example.placementadmin.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactPerson;
    private String email;
    private String phone;
    private Boolean approved = false;

    @Lob
    private String detailsJson;
}
