package com.example.placementadmin.repositories;

import com.example.placementadmin.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> { }
