package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Facility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    // Fixed method now matches the entity field
    Optional<Facility> findByCode(String code);

    // Corrected method name to match facilityName field
    List<Facility> findByFacilityNameContainingIgnoreCase(String name);
    
    Page<Facility> findByFacilityNameContainingIgnoreCase(String name, Pageable pageable);

    List<Facility> findByActiveTrue();
    List<Facility> findByActiveFalse();
    List<Facility> findByRequiresApprovalTrue();
    List<Facility> findByAddressContainingIgnoreCase(String addressTerm);

    @Query("SELECT f FROM Facility f WHERE f.latitude IS NOT NULL AND f.longitude IS NOT NULL")
    List<Facility> findWithGeoCoordinates();

    long countByActiveTrue();
    long countByActiveFalse();

    @Query("SELECT f FROM Facility f WHERE f.parkingCapacity > 0")
    List<Facility> findWithParking();

    @Query("SELECT f FROM Facility f WHERE f.parkingCapacity > 0 ORDER BY f.parkingCapacity DESC")
    List<Facility> findWithParkingSortedByCapacity();

    List<Facility> findByVisitorCapacityGreaterThanEqual(int threshold);

    @Query("SELECT f FROM Facility f WHERE f.email LIKE %:emailDomain")
    List<Facility> findByEmailDomain(String emailDomain);
}