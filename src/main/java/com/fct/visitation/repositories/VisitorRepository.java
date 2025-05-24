package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.enums.VehicleType;
import com.fct.visitation.models.enums.VisitorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    // Existing methods
    List<Visitor> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    Optional<Visitor> findByEmail(String email);
    Optional<Visitor> findByPhoneNumber(String phoneNumber);
    List<Visitor> findByStatus(VisitorStatus status);
    List<Visitor> findByVehicle_VehicleType(VehicleType vehicleType);
    Optional<Visitor> findByQrCode(String qrCode);
    List<Visitor> findByVehicleIsNotNull();

    // New method for counting check-ins between dates
    long countByCheckInTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}