package com.example.placementadmin.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String hallTicket;
    private String email;
    private String qualification;
    private String course;
    private Integer passYear;
    private String resumePath;
}
