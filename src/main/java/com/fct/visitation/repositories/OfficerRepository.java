package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.Officer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {
    
    /**
     * Find an officer by email
     */
    Optional<Officer> findByEmail(String email);
    
    /**
     * Find officers by first name or last name containing the search term
     */
    @Query("SELECT o FROM Officer o WHERE " +
           "LOWER(o.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(o.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Officer> findByNameContaining(@Param("searchTerm") String searchTerm);
    
    /**
     * Find officers by first name or last name containing the search term with pagination
     */
    @Query("SELECT o FROM Officer o WHERE " +
           "LOWER(o.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(o.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Officer> findByNameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);
    
    /**
     * Find officers by facility
     */
    List<Officer> findByFacility(Facility facility);
    
    /**
     * Find officers by facility with pagination
     */
    Page<Officer> findByFacility(Facility facility, Pageable pageable);
    
    /**
     * Find active officers
     */
    List<Officer> findByActiveTrue();
    
    /**
     * Find officers by department
     */
    List<Officer> findByDepartmentContainingIgnoreCase(String department);
    
    /**
     * Find officers by facility and department
     */
    List<Officer> findByFacilityAndDepartment(Facility facility, String department);
    
    /**
     * Count officers by facility
     */
    long countByFacility(Facility facility);
    
    /**
     * Count active officers
     */
    long countByActiveTrue();
}