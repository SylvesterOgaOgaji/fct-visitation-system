package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.SecurityPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityPersonnelRepository extends JpaRepository<SecurityPersonnel, Long> {
    /**
     * Find security personnel by username
     * @param username The username to search for
     * @return Optional containing the security personnel if found
     */
    Optional<SecurityPersonnel> findByUsername(String username);
}