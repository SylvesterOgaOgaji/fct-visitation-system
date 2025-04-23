package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.models.entity.Vehicle;
import com.fct.visitation.repositories.CarDetailsRepository;
import com.fct.visitation.repositories.VehicleRepository;
import com.fct.visitation.services.interfaces.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository = null; 
    private final CarDetailsRepository carDetailsRepository = null; // ✅

    @Override
    @Transactional
    public Vehicle registerVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Optional<CarDetails> findById(Long id) {
    Optional<CarDetails> carDetailsOpt = carDetailsRepository.findByVehicle_VehicleId(id); // ✅ Correct method name
    // ...
 
        // If not found, you might want to create a new CarDetails or handle this differently
        if (carDetailsOpt.isEmpty()) {
            // Optional: try to find the vehicle and create CarDetails if it exists
            Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
            if (vehicleOpt.isPresent()) {
                CarDetails newCarDetails = new CarDetails();
                newCarDetails.setVehicle(vehicleOpt.get());
                return Optional.of(carDetailsRepository.save(newCarDetails));
            }
        }
        
        return carDetailsOpt;
    }

    @Override
    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> findVehiclesByModel(String model) {
        return vehicleRepository.findByModel(model);
    }

    @Override
    public List<Vehicle> findVehiclesByColor(String color) {
        return vehicleRepository.findByColor(color);
    }

    @Override
    public List<Vehicle> findVehiclesByType(Vehicle.VehicleType vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType);
    }
}