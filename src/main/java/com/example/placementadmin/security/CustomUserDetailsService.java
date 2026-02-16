package com.example.placementadmin.security;

import com.example.placementadmin.entities.Admin;
import com.example.placementadmin.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found: " + email));

        // build Spring Security User (password must be stored as BCRYPT hash)
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(admin.getEmail())
                .password(admin.getPasswordHash())
                .roles("ADMIN")
                .build();
    }
}
