package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.RentedCarDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentedCarDriverRepository extends JpaRepository<RentedCarDriver, Long> {
    /**
     * Find driver by visitor ID
     * @param visitorId ID of the visitor
     * @return Optional of rented car driver
     */
    Optional<RentedCarDriver> findByVisitorVisitorId(Long visitorId);
    
    /**
     * Find drivers by verification status
     * @param status Verification status
     * @return List of rented car drivers
     */
    List<RentedCarDriver> findByVerificationStatus(RentedCarDriver.VerificationStatus status);
    
    /**
     * Find driver by NIN
     * @param nin National Identification Number
     * @return Optional of rented car driver
     */
    Optional<RentedCarDriver> findByNin(String nin);
    
    /**
     * Find driver by license number
     * @param licenseNumber Driver's license number
     * @return Optional of rented car driver
     */
    Optional<RentedCarDriver> findByLicenseNumber(String licenseNumber);
}