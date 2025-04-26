package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.RentedCarDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentedCarDriverRepository extends JpaRepository<RentedCarDriver, Long> {

    // Fixed method names to match entity properties
    Optional<RentedCarDriver> findByVisitor_Id(Long visitorId);
    
    List<RentedCarDriver> findByVerificationStatus(RentedCarDriver.VerificationStatus status);
    
    Optional<RentedCarDriver> findByNin(String nin);
    
    Optional<RentedCarDriver> findByDriverLicense(String driverLicense);  // Changed to match entity field name

    // Add this if you need to search by license number using a different name
    default Optional<RentedCarDriver> findByLicenseNumber(String licenseNumber) {
        return findByDriverLicense(licenseNumber);
    }
}