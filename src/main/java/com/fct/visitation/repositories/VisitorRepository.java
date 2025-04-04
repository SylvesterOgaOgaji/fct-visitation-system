package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    Optional<Visitor> findByQrCode(String qrCode);
    Optional<Visitor> findByEmail(String email);
    Optional<Visitor> findByNin(String nin);
    List<Visitor> findByStatus(Visitor.VisitorStatus status);
    List<Visitor> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Visitor> findByOfficerOfficerId(Long officerId);
    List<Visitor> findByFacilityFacilityId(Long facilityId);
}
