package com.example.placementadmin.services;

import com.example.placementadmin.entities.AuditLog;
import com.example.placementadmin.repositories.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminActionLogger {

    @Autowired
    private AuditLogRepository auditLogRepo;

    public void log(Long adminId, String action, String entity, String entityId, String details) {
        AuditLog log = new AuditLog();
        log.setAdminId(adminId);
        log.setAction(action);
        log.setEntity(entity);
        log.setEntityId(entityId);
        log.setDetails(details);
        auditLogRepo.save(log);
    }
}
