package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.entity.VisitorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    Optional<Visitor> findByEmail(String email);
    Optional<Visitor> findByPhoneNumber(String phoneNumber);
    List<Visitor> findByStatus(VisitorStatus status);
    Optional<Visitor> findByQrCode(String qrCode);
    List<Visitor> findByCarTypeNotNull();
}