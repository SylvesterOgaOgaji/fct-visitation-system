package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.RentedCarDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentedCarDriverRepository extends JpaRepository<RentedCarDriver, Long> {
    /**
     * Find driver by National Identification Number (NIN)
     * @param nin National Identification Number
     * @return Optional of RentedCarDriver
     */
    Optional<RentedCarDriver> findByNin(String nin);

    /**
     * Find driver by license number
     * @param licenseNumber Driver's license number
     * @return Optional of RentedCarDriver
     */
    Optional<RentedCarDriver> findByLicenseNumber(String licenseNumber);
}