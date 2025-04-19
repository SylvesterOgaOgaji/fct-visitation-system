package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.SecurityIncident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityIncidentRepository extends JpaRepository<SecurityIncident, Long> {
    /**
     * Find security incidents by their status
     * @param status The status to filter by
     * @return List of security incidents with the specified status
     */
    List<SecurityIncident> findByStatus(SecurityIncident.IncidentStatus status);
}