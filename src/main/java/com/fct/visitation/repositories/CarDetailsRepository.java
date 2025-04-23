package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {

    // NEW METHOD ADDED TO FIX COMPILATION ERROR
    /**
     * Find car details by associated vehicle ID
     * @param vehicleId ID of the vehicle
     * @return Optional of car details
     */
    Optional<CarDetails> findByVehicle_VehicleId(Long vehicleId);

    // Existing methods remain unchanged
    Optional<CarDetails> findByVisitorVisitorId(Long visitorId);
    Optional<CarDetails> findByDriverDriverId(Long driverId);
    Optional<CarDetails> findByRegistrationNumber(String registrationNumber);
    List<CarDetails> findByCarType(CarDetails.CarType carType);
}