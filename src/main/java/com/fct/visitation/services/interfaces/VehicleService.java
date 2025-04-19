package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.models.entity.RentedCarDriver;

import java.util.Optional;

public interface VehicleService {
    /**
     * Find car details by visitor ID
     * @param visitorId ID of the visitor
     * @return Optional of car details
     */
    Optional<CarDetails> findByVisitorId(Long visitorId);

    /**
     * Find car details by ID
     * @param vehicleId ID of the vehicle
     * @return Optional of car details
     */
    Optional<CarDetails> findById(Long vehicleId);

    /**
     * Find car details by driver ID
     * @param driverId ID of the driver
     * @return Optional of car details
     */
    Optional<CarDetails> findByDriverId(Long driverId);

    /**
     * Register a vehicle
     * @param carDetails Vehicle details to register
     * @return Registered vehicle details
     */
    CarDetails registerVehicle(CarDetails carDetails);

    /**
     * Register a vehicle with a driver
     * @param carDetails Vehicle details
     * @param driver Driver details
     * @return Registered vehicle details
     */
    CarDetails registerVehicleWithDriver(CarDetails carDetails, RentedCarDriver driver);

    /**
     * Find driver by ID
     * @param driverId ID of the driver
     * @return Optional of driver details
     */
    Optional<RentedCarDriver> findDriverById(Long driverId);

    /**
     * Verify a driver's status
     * @param driverId ID of the driver
     * @param verificationStatus New verification status
     * @param verifiedById ID of the personnel performing verification
     * @return Optional of updated rented car driver
     */
    Optional<RentedCarDriver> verifyDriver(Long driverId, 
                                           RentedCarDriver.VerificationStatus verificationStatus, 
                                           Long verifiedById);

    /**
     * Delete a vehicle by its ID
     * @param vehicleId ID of the vehicle to delete
     */
    void deleteVehicle(Long vehicleId);
}