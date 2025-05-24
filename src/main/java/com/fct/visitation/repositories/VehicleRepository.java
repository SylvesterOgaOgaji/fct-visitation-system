package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Vehicle;
import com.fct.visitation.models.enums.VehicleType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
    List<Vehicle> findByModel(String model);
    List<Vehicle> findByColor(String color);
    List<Vehicle> findByVehicleType(VehicleType vehicleType);
}