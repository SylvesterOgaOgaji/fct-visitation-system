package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);
    // Change Role to String since Admin.role is a String
    Optional<Admin> findByRole(String role);
}