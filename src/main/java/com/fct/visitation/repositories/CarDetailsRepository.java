package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
    // Changed method names to match entity relationships
    Optional<CarDetails> findByVehicle_Id(Long vehicleId);
    Optional<CarDetails> findByVisitor_Id(Long visitorId);
    Optional<CarDetails> findByDriver_Id(Long driverId);  // Now matches Driver.id
    Optional<CarDetails> findByRegistrationNumber(String registrationNumber);
    List<CarDetails> findByCarType(CarDetails.CarType carType);
}