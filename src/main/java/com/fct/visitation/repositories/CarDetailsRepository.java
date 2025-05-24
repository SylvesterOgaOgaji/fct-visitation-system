package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
    void deleteByVehicle_Id(Long vehicleId);
    Optional<CarDetails> findByVehicle_Id(Long vehicleId);
    Optional<CarDetails> findByVisitor_Id(Long visitorId);
    Optional<CarDetails> findByRegistrationNumber(String registrationNumber);
}