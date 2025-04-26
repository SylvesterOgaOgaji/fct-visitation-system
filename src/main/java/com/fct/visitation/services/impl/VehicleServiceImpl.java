package com.fct.visitation.services.impl;

import com.fct.visitation.exceptions.ResourceNotFoundException;
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
    private final CarDetailsRepository carDetailsRepository = null;

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
        // Changed to search by CarDetails' own ID
        return carDetailsRepository.findById(id);
    }

    // Added new method to find CarDetails by vehicle ID
    public CarDetails findCarDetailsByVehicleId(Long vehicleId) {
        return carDetailsRepository.findByVehicle_Id(vehicleId)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID: " + vehicleId));
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