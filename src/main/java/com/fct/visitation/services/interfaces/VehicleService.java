package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.models.entity.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
    // CRUD Operations
    Vehicle registerVehicle(Vehicle vehicle);
    Optional<Vehicle> getVehicleById(Long id);
    
    // This method should now work with CarDetails
    Optional<CarDetails> findById(Long id);
    
    Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber);
    List<Vehicle> getAllVehicles();
    Vehicle updateVehicle(Vehicle vehicle);
    void deleteVehicle(Long id);

    // Query Methods
    List<Vehicle> findVehiclesByModel(String model);
    List<Vehicle> findVehiclesByColor(String color);
    List<Vehicle> findVehiclesByType(Vehicle.VehicleType vehicleType);
}