package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.repositories.VehicleRepository;
import com.fct.visitation.repositories.CarDetailsRepository;
import com.fct.visitation.models.entity.Vehicle;
import com.fct.visitation.models.enums.VehicleType;
import com.fct.visitation.services.interfaces.VehicleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CarDetailsRepository carDetailsRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, CarDetailsRepository carDetailsRepository) {
        this.vehicleRepository = vehicleRepository;
        this.carDetailsRepository = carDetailsRepository;
    }

    @Override
    @Transactional
    public Vehicle registerVehicle(Vehicle vehicle) {
        if (vehicle.getRegistrationNumber() == null || 
            ((String) vehicle.getRegistrationNumber()).trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be empty");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<CarDetails> findById(Long id) {
        return carDetailsRepository.findById(id);
    }

    @Override
    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber) {
        return vehicleRepository.findByRegistrationNumber(registrationNumber);
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
    public List<Vehicle> findVehiclesByType(VehicleType vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType);
    }
}