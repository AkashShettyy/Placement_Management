package com.example.placementadmin.repositories;

import com.example.placementadmin.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStatus(String status);
}
