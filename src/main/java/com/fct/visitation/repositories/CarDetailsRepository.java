package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
    /**
     * Find car details by visitor ID
     * @param visitorId ID of the visitor
     * @return Optional of car details
     */
    Optional<CarDetails> findByVisitorVisitorId(Long visitorId);

    /**
     * Find car details by driver ID
     * @param driverId ID of the driver
     * @return Optional of car details
     */
    Optional<CarDetails> findByDriverDriverId(Long driverId);

    /**
     * Find car details by registration number
     * @param registrationNumber Vehicle registration number
     * @return Optional of car details
     */
    Optional<CarDetails> findByRegistrationNumber(String registrationNumber);

    /**
     * Find car details by car type
     * @param carType Type of the car
     * @return List of car details
     */
    List<CarDetails> findByCarType(CarDetails.CarType carType);
}